package jmotor.web.network.filter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.yunat.network.exception.NotFoundConfigurationException;
import jmotor.core.log.Logger;
import jmotor.core.log.LoggerManager;
import jmotor.util.CollectionUtils;
import jmotor.util.ResourceUtils;
import jmotor.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Component:
 * Description:
 * Date: 12-6-22
 *
 * @author Andy.Ai
 */
public class NetworkFilter implements Filter, NetworkFilterMBean {
    private static Logger logger = LoggerManager.getLogger(NetworkFilter.class);
    private List<String> deny;
    private List<String> allow;
    private String refusedPath;
    private String x_match = "all";
    private LoadingCache<String, Boolean> filterCache;
    private String configLocation = "config/filter/networkFilter.config";
    private String configFireChangeUrl = "/config/network_filter/fire_change";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String _configLocation = filterConfig.getInitParameter("configLocation");
        if (StringUtils.isNotBlank(_configLocation)) {
            configLocation = _configLocation;
        }
        String _configFireChangeUrl = filterConfig.getInitParameter("configFireChangeUrl");
        if (StringUtils.isNotBlank(_configFireChangeUrl)) {
            configFireChangeUrl = _configFireChangeUrl;
        }
        String maximumSize = filterConfig.getInitParameter("maximumSize");
        initFilterCache(StringUtils.isNotBlank(maximumSize) ? Long.valueOf(maximumSize) : 10000);
        try {
            fireChangeConfiguration();
        } catch (NotFoundConfigurationException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String remoteAddress = request.getRemoteAddr();
        String x_forwarded_for = ((HttpServletRequest) request).getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(x_forwarded_for)) {
            remoteAddress = x_forwarded_for;
        }
        String contextPath = ((HttpServletRequest) request).getContextPath();
        String requestUrl = ((HttpServletRequest) request).getRequestURL().toString();
        if (requestUrl.endsWith(contextPath + configFireChangeUrl) &&
                ("127.0.0.1".equals(remoteAddress) || "0:0:0:0:0:0:0:1".equals(remoteAddress))) {
            try {
                logger.debug("fire change network filter configuration");
                fireChangeConfiguration();
                String message = "fire change network filter configuration successful!";
                logger.info(message);
                response.getWriter().println(message);
            } catch (NotFoundConfigurationException e) {
                String message = "fire change network filter configuration failure!";
                logger.error(message);
                response.getWriter().println(message);
                throw new ServletException(e.getMessage(), e);
            }
        } else {
            try {
                boolean passed = filterCache.get(remoteAddress);
                if (passed) {
                    logger.info("Allow: " + remoteAddress);
                    chain.doFilter(request, response);
                } else {
                    logger.error("Deny: " + remoteAddress);
                    if (StringUtils.isNotBlank(refusedPath)) {
                        request.getRequestDispatcher(refusedPath).forward(request, response);
                    } else {
                        response.getWriter().println("server refused");
                    }
                }
            } catch (ExecutionException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void destroy() {
        deny = null;
        allow = null;
        filterCache = null;
    }

    @Override
    public void fireChangeConfiguration() throws NotFoundConfigurationException {
        try {
            Properties properties = ResourceUtils.loadProperties(configLocation, true);
            String _x_match = ResourceUtils.getProperty(properties, "x_match");
            if (StringUtils.isNotBlank(_x_match)) {
                x_match = _x_match;
            }
            String allows = ResourceUtils.getProperty(properties, "allow");
            if (StringUtils.isNotBlank(allows)) {
                allow = new ArrayList<String>(10);
                handleAddresses(allow, allows);
            }
            String denies = ResourceUtils.getProperty(properties, "deny");
            if (StringUtils.isNotBlank(denies)) {
                deny = new ArrayList<String>(10);
                handleAddresses(deny, denies);
            }
            String _refusedPath = ResourceUtils.getProperty(properties, "refusedPath");
            if (StringUtils.isNotBlank(_refusedPath)) {
                refusedPath = _refusedPath;
            }
            Map<String, Boolean> allCaches = filterCache.asMap();
            if (CollectionUtils.isNotEmpty(allCaches)) {
                for (String key : allCaches.keySet()) {
                    filterCache.refresh(key);
                }
            }
        } catch (IOException e) {
            logger.error("Can't load network configuration", e);
            throw new NotFoundConfigurationException(e.getMessage(), e);
        }
    }

    private void handleAddresses(List<String> filters, String addresses) {
        String[] addressFilters = StringUtils.split(addresses, StringUtils.COMMA);
        for (String addressFilter : addressFilters) {
            addressFilter = addressFilter.trim();
            if (addressFilter.contains("~")) {
                int dotIndex = addressFilter.lastIndexOf(".") + 1;
                String prefix = addressFilter.substring(0, dotIndex);
                String[] range = addressFilter.substring(dotIndex).split("~");
                for (int i = Integer.valueOf(range[0]); i < Integer.valueOf(range[1]); i++) {
                    filters.add(prefix + i);
                }
            } else {
                filters.add(addressFilter);
            }
        }
    }

    private void initFilterCache(long maximumSize) {
        filterCache = CacheBuilder.newBuilder()
                .initialCapacity(100)
                .maximumSize(maximumSize)
                .expireAfterWrite(Integer.MAX_VALUE, TimeUnit.DAYS)
                .build(new CacheLoader<String, Boolean>() {
                    @Override
                    public Boolean load(String address) throws Exception {
                        return filterAddress(address);
                    }
                });
    }

    private boolean filterAddress(String remoteAddress) {
        if (CollectionUtils.isEmpty(allow) && CollectionUtils.isEmpty(deny)) {
            return true;
        }
        String[] addresses = StringUtils.split(remoteAddress, ",");
        boolean allMatchedPattern = "all".equals(x_match);
        boolean allowPattern;
        List<String> filter;
        if (CollectionUtils.isNotEmpty(allow)) {
            filter = allow;
            allowPattern = true;
        } else {
            filter = deny;
            allowPattern = false;
        }
        boolean passed = false;
        for (String address : addresses) {
            passed = validationAddress(filter, address);
            if (allMatchedPattern && allowPattern && !passed) {
                break;
            } else if (!allMatchedPattern && allowPattern && passed) {
                break;
            } else if (allMatchedPattern && !allowPattern && !passed) {
                passed = true;
                break;
            } else if (!allMatchedPattern && !allowPattern && passed) {
                passed = false;
                break;
            }
        }
        return passed;
    }

    private boolean validationAddress(List<String> filters, String address) {
        String _address = StringUtils.replace(address, " ", "");
        for (String filter : filters) {
            if (filter.equals(_address)) {
                return true;
            } else if (StringUtils.contains(filter, StringUtils.ANY_MARK)) {
                String separator = ".";
                if (StringUtils.contains(_address, ":")) {
                    separator = ":";
                }
                if (canCompared(filter, address, separator)) {
                    String[] addressBytes = StringUtils.split(_address, separator);
                    String[] filterBytes = StringUtils.split(filter, separator);
                    boolean matched = false;
                    for (int i = 0; i < filterBytes.length; i++) {
                        if (StringUtils.ANY_MARK.equals(filterBytes[i])) {
                            continue;
                        }
                        matched = filterBytes[i].equals(addressBytes[i]);
                        if (!matched) {
                            break;
                        }
                    }
                    if (matched) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canCompared(String filter, String address, String separator) {
        return StringUtils.contains(filter, separator) &&
                (StringUtils.countMatches(filter, separator) == StringUtils.countMatches(address, separator));
    }
}
