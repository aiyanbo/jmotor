package jmotor.core.cache;

import java.io.InputStream;

/**
 * Component:Cache
 * Description:Cache provider
 * Date: 11-10-10
 *
 * @author Andy.Ai
 */
public interface CacheProvider {
    Cache getCache(String cacheName);

    void start(InputStream inputStream);

    void start(String name);

    void stop();
}
