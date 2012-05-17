package jmotor.core.ioc.pool.impl;

import jmotor.core.cache.Cache;
import jmotor.core.ioc.cache.CacheManager;
import jmotor.core.ioc.config.ElementConfiguration;
import jmotor.core.ioc.pool.ElementConfigurationPool;

import java.util.Collection;

/**
 * Component:IOC
 * Description:
 * Date: 11-8-19
 *
 * @author Andy.Ai
 */
public class ElementConfigurationPoolImpl implements ElementConfigurationPool {
    private Cache elementConfigurationCache = CacheManager.getCache("elementConfigurationCache");

    @Override
    public void put(ElementConfiguration elementConfiguration) {
        elementConfigurationCache.put(elementConfiguration.getId(), elementConfiguration);
    }

    @Override
    public void putAll(Collection<ElementConfiguration> elementConfigurations) {
        for (ElementConfiguration elementConfiguration : elementConfigurations) {
            put(elementConfiguration);
        }
    }

    @Override
    public ElementConfiguration get(String key) {
        return elementConfigurationCache.get(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ElementConfiguration getByClassName(String className) {
        for (ElementConfiguration elementConfiguration : (Iterable<ElementConfiguration>) elementConfigurationCache) {
            if (elementConfiguration.getClazz().equals(className)) {
                return elementConfiguration;
            }
        }
        return null;
    }
}
