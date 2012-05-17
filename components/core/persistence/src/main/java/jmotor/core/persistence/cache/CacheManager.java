package jmotor.core.persistence.cache;

import jmotor.core.cache.Cache;
import jmotor.core.cache.CacheProvider;
import jmotor.core.cache.ehcache.EhCacheProvider;
import jmotor.util.StreamUtils;

/**
 * Component:
 * Description:
 * Date: 12-4-12
 *
 * @author Andy.Ai
 */
public class CacheManager {
    private static CacheProvider cacheProvider;

    private CacheManager() {
    }

    public static Cache getCache(String cacheName) {
        if (cacheProvider == null) {
            cacheProvider = new EhCacheProvider();
            cacheProvider.start(StreamUtils.getStream4ClassPath("cache/persistence_cache.xml"));
        }
        return cacheProvider.getCache(cacheName);
    }
}
