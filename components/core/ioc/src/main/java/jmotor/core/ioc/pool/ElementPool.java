package jmotor.core.ioc.pool;

import jmotor.core.ioc.meta.Element;
import jmotor.core.ioc.repository.Repository;

import java.util.Collection;

/**
 * Component:
 * Description:
 * Date: 11-8-19
 *
 * @author Andy.Ai
 */
public interface ElementPool extends Repository {
    void put(Element element);

    void put(String key, Object object);

    void putAll(Collection<Element> elements);

    Object get(String key);
}
