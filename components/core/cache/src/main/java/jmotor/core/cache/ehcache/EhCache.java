package jmotor.core.cache.ehcache;

import jmotor.core.cache.Cache;
import net.sf.ehcache.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Component:Cache
 * Description:Eh cache implementation
 * Date: 11-12-10
 *
 * @author Andy.Ai
 */
public class EhCache implements Cache {
    private static final long serialVersionUID = -6849794470754667710L;
    private net.sf.ehcache.Cache cache;

    public String getName() {
        return cache.getName();
    }

    public void put(Object key, Object value) {
        Element element = new Element(key, value, serialVersionUID);
        cache.put(element);
    }

    public boolean remove(Object key) {
        return cache.remove(key);
    }

    public boolean removeObject(Object value) {
        Object removeKey = null;
        for (Object key : cache.getKeys()) {
            Object cacheValue = get(key);
            if (value.equals(cacheValue)) {
                removeKey = key;
                break;
            }
        }
        return cache.remove(removeKey);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key) {
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return (T) element.getObjectValue();
    }

    public List getKeys() {
        return cache.getKeys();
    }

    @SuppressWarnings("unchecked")
    public Map toMap() {
        Map values = new HashMap();
        for (Object key : cache.getKeys()) {
            values.put(key, get(key));
        }
        return values;
    }

    public Iterator iterator() {
        List<Object> values = new ArrayList<Object>();
        for (Object key : cache.getKeys()) {
            values.add(get(key));
        }
        return values.iterator();
    }

    public long getElementCountInMemory() {
        return cache.getMemoryStoreSize();
    }

    public long getElementCountOnDisk() {
        return cache.getDiskStoreSize();
    }

    @Override
    public boolean containsKey(Object key) {
        return cache.isKeyInCache(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return cache.isValueInCache(value);
    }

    public void setCache(net.sf.ehcache.Cache cache) {
        this.cache = cache;
    }
}
