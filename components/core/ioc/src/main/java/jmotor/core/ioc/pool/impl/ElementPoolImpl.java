package jmotor.core.ioc.pool.impl;

import jmotor.core.cache.Cache;
import jmotor.core.ioc.cache.CacheManager;
import jmotor.core.ioc.meta.Element;
import jmotor.core.ioc.pool.ElementPool;

import java.util.Collection;

/**
 * Component:
 * Description:
 * Date: 11-8-19
 *
 * @author Andy.Ai
 */
public class ElementPoolImpl implements ElementPool {
    private Cache elementCache = CacheManager.getCache("elementCache");

    public void put(Element element) {
        put(element.getKey(), element.getValue());
    }

    public void put(String key, Object object) {
        elementCache.put(key, object);
    }

    public void putAll(Collection<Element> elements) {
        for (Element element : elements) {
            put(element);
        }
    }

    public Object get(String key) {
        return elementCache.get(key);
    }
}
