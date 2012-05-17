package jmotor.core.i18n.impl;

import jmotor.core.i18n.I18nManager;
import jmotor.core.i18n.I18nResourceProperties;
import jmotor.util.BooleanUtils;
import jmotor.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * Component:I18n
 * Description:I18n manager default implementation
 * Date: 12-3-12
 *
 * @author Andy.Ai
 */
public class I18nManagerImpl implements I18nManager {
    private static final String DEFAULT_NAME = "default";
    private Map<String, Boolean> baseNames = new HashMap<String, Boolean>();
    private Map<String, Map<Locale, Properties>> resourceBundleCache = new HashMap<String, Map<Locale, Properties>>();

    @Override
    public String getI18nValue(String key) {
        return getI18nValue(key, Locale.getDefault());
    }

    @Override
    public String getI18nValue(String key, Locale locale) {
        for (String baseName : baseNames.keySet()) {
            if (resourceBundleCache.containsKey(baseName)) {
                Map<Locale, Properties> resources = resourceBundleCache.get(baseName);
                if (resources != null) {
                    Properties properties = resources.get(locale);
                    if (properties != null) {
                        String value = properties.getProperty(key);
                        if (value != null) {
                            return value;
                        }
                        continue;
                    }
                }
            }
            Properties properties = loadResources(baseName, locale);
            if (properties != null) {
                String value = properties.getProperty(key);
                if (value != null) {
                    return value;
                }
            }
        }
        Map<Locale, Properties> resources = resourceBundleCache.get(DEFAULT_NAME);
        if (resources != null) {
            Properties properties = resources.get(locale);
            if (properties != null) {
                return properties.getProperty(key);
            }
        }
        return null;
    }

    @Override
    public Properties getResources(String[] baseNames, Locale locale) {
        for (String baseName : baseNames) {
            register(baseName);
        }
        return null;
    }

    @Override
    public Properties getResources(String baseNames, Locale locale) {

        return null;
    }

    @Override
    public void register(String baseName) {
        baseNames.put(baseName, false);
    }

    @Override
    public Properties register(String path, Locale locale) throws IOException {
        return register(StreamUtils.getStream4ClassPath(path), locale);
    }

    @Override
    public void register4FileSystem(String baseName) {
        baseNames.put(baseName, true);
    }

    @Override
    public Properties register4FileSystem(String path, Locale locale) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = StreamUtils.getStream4FileSystem(path);
            return register(inputStream, locale);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    @Override
    public Properties register(InputStream inputStream, Locale locale) throws IOException {
        I18nResourceProperties properties = new I18nResourceProperties();
        synchronized (this) {
            try {
                properties.load(inputStream);
            } finally {
                inputStream.close();
            }
            putResources(DEFAULT_NAME, locale, properties);
        }
        return properties;
    }

    @Override
    public Properties register(byte[] content, Locale locale) throws UnsupportedEncodingException {
        I18nResourceProperties properties = new I18nResourceProperties();
        properties.load(content, "UTF-8");
        putResources(DEFAULT_NAME, locale, properties);
        return properties;
    }

    private Properties loadResources(String baseName, Locale locale) {
        Boolean fileSystemPath = baseNames.get(baseName);
        StringBuilder pathBuilder = new StringBuilder(baseName.replace("\\", File.separator));
        pathBuilder.append(File.separator).append("resource_");
        pathBuilder.append(locale.getLanguage().toLowerCase()).append('_').append(locale.getCountry().toUpperCase());
        try {
            if (BooleanUtils.valueOf(fileSystemPath)) {
                return register4FileSystem(pathBuilder.toString(), locale);
            } else {
                return register(pathBuilder.toString(), locale);
            }
        } catch (IOException e) {
            return null;
        }
    }

    private void putResources(String baseName, Locale locale, Properties properties) {
        Map<Locale, Properties> resourceBundle = resourceBundleCache.get(baseName);
        if (resourceBundle == null) {
            resourceBundle = new HashMap<Locale, Properties>();
            resourceBundleCache.put(baseName, resourceBundle);
        }
        Properties resources = resourceBundle.get(locale);
        if (resources == null) {
            resourceBundle.put(locale, properties);
        } else {
            resources.putAll(properties);
        }
    }
}
