package jmotor.core.ioc.cache;

import jmotor.core.cache.Cache;
import jmotor.core.cache.CacheProvider;
import jmotor.core.cache.ehcache.EhCacheProvider;
import jmotor.util.StreamUtils;

/**
 * Component:IOC
 * Description:Ioc cache manager.
 * Date: 11-12-10
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
            cacheProvider.start(StreamUtils.getStream4ClassPath("cache/ioc_caches.xml"));
        }
        return cacheProvider.getCache(cacheName);
    }
}
