package jmotor.core.ioc.pool.impl;

import jmotor.core.cache.Cache;
import jmotor.core.ioc.cache.CacheManager;
import jmotor.core.ioc.config.EntryWrapperConfiguration;
import jmotor.core.ioc.pool.EntryWrapperConfigurationPool;

import java.util.Collection;

/**
 * Component:
 * Description:
 * Date: 11-11-14
 *
 * @author Andy.Ai
 */
public class EntryWrapperConfigurationPoolImpl implements EntryWrapperConfigurationPool {
    private Cache collectionConfigurationCache = CacheManager.getCache("collectionConfigurationCache");

    public void put(EntryWrapperConfiguration entryWrapperConfiguration) {
        collectionConfigurationCache.put(entryWrapperConfiguration.getId(), entryWrapperConfiguration);
    }

    public void putAll(Collection<EntryWrapperConfiguration> entryWrapperConfigurations) {
        for (EntryWrapperConfiguration entryWrapperConfiguration : entryWrapperConfigurations) {
            put(entryWrapperConfiguration);
        }
    }

    public EntryWrapperConfiguration get(String key) {
        return collectionConfigurationCache.get(key);
    }
}
