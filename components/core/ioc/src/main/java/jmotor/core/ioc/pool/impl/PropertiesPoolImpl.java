package jmotor.core.ioc.pool.impl;

import jmotor.core.cache.Cache;
import jmotor.core.ioc.cache.CacheManager;
import jmotor.core.ioc.pool.PropertiesPool;

import java.util.Map;
import java.util.Properties;

/**
 * Component:
 * Description:
 * Date: 11-11-14
 *
 * @author Andy.Ai
 */
public class PropertiesPoolImpl implements PropertiesPool {
    private Cache propertiesCache = CacheManager.getCache("propertiesCache");

    public void putAll(Map<String, String> properties) {
        for (String key : properties.keySet()) {
            propertiesCache.put(key, properties.get(key));
        }
    }

    public void put(Properties properties) {
        for (Object key : properties.keySet()) {
            put(key.toString(), properties.getProperty(key.toString()));
        }
    }

    public void put(String key, String value) {
        propertiesCache.put(key, value);
    }

    public String get(String key) {
        return propertiesCache.get(key);
    }
}
