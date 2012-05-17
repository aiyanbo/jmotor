package jmotor.core.ioc.pool.impl;

import jmotor.core.cache.Cache;
import jmotor.core.ioc.cache.CacheManager;
import jmotor.core.ioc.context.ApplicationContext;
import jmotor.core.ioc.context.BeanConfigurationContext;
import jmotor.core.ioc.pool.BeanConfigurationPool;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 11-8-22
 *
 * @author Andy.Ai
 */
public class BeanConfigurationCachePoolImpl implements BeanConfigurationPool {
    private Cache beanConfigContextCache = CacheManager.getCache("beanConfigContextCache");

    public String get(String key) {
        if (ApplicationContext.debug) {
            return beanConfigContextCache.get(key);
        }
        System.err.println("debug pattern disabled");
        return null;
    }

    public void put(BeanConfigurationContext beanConfigurationContext) {
        put(beanConfigurationContext.getKey(), beanConfigurationContext.getContext());
    }

    public void put(String key, String context) {
        if (ApplicationContext.debug) {
            beanConfigContextCache.put(key, context);
        }
    }

    public void putAll(Collection<BeanConfigurationContext> beanConfigurationContexts) {
        for (BeanConfigurationContext beanConfigurationContext : beanConfigurationContexts) {
            put(beanConfigurationContext);
        }
    }
}
