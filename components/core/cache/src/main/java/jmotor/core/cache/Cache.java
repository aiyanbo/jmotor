package jmotor.core.cache;

import java.util.List;
import java.util.Map;

/**
 * Component:Cache
 * Description:Cache
 * Date: 11-11-25
 *
 * @author Andy.Ai
 */
public interface Cache extends Iterable {
    String getName();

    void put(Object key, Object value);

    boolean remove(Object key);

    boolean removeObject(Object value);

    <T> T get(Object key);

    List getKeys();

    Map toMap();

    long getElementCountInMemory();

    long getElementCountOnDisk();

    boolean containsKey(Object key);

    boolean containsValue(Object value);
}
