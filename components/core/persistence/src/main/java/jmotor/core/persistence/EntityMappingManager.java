package jmotor.core.persistence;

import jmotor.core.persistence.meta.TableMeta;

/**
 * Component:
 * Description:
 * Date: 12-4-11
 *
 * @author Andy.Ai
 */
public interface EntityMappingManager {
    TableMeta getEntity(Object entity);

    TableMeta getEntity(String entityName);

    TableMeta registerEntity(Class<?> entityClass);

    boolean exists(String name);

    boolean exists(Object entity);
}
