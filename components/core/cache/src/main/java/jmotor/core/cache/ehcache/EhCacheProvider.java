package jmotor.core.cache.ehcache;

import jmotor.core.cache.Cache;
import jmotor.core.cache.CacheProvider;
import net.sf.ehcache.CacheManager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Component:Cache
 * Description:Eh cache provider implementation
 * Date: 11-10-10
 *
 * @author Andy.Ai
 */
public class EhCacheProvider implements CacheProvider {
    private Map<String, EhCache> cacheMapper = new HashMap<String, EhCache>();
    private CacheManager cacheManager;

    public Cache getCache(String cacheName) {
        EhCache cache = cacheMapper.get(cacheName);
        if (cache == null) {
            net.sf.ehcache.Cache ehCache = cacheManager.getCache(cacheName);
            if (ehCache == null) {
                cacheManager.addCache(cacheName);
                ehCache = cacheManager.getCache(cacheName);
            }
            cache = new EhCache();
            cache.setCache(ehCache);
            cacheMapper.put(cacheName, cache);
        }
        return cache;
    }

    public void start(InputStream inputStream) {
        cacheManager = CacheManager.create(inputStream);
    }

    public void start(String name) {
        cacheManager = CacheManager.create(name);
    }

    public void stop() {
        cacheManager.shutdown();
    }
}
