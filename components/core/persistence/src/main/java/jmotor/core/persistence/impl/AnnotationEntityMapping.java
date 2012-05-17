package jmotor.core.persistence.impl;

import jmotor.core.cache.Cache;
import jmotor.core.log.Logger;
import jmotor.core.log.LoggerManager;
import jmotor.core.persistence.EntityMappingManager;
import jmotor.core.persistence.cache.CacheManager;
import jmotor.core.persistence.exception.EntityMappingException;
import jmotor.core.persistence.meta.ColumnMeta;
import jmotor.core.persistence.meta.TableMeta;
import jmotor.util.ClassUtils;
import jmotor.util.CollectionUtils;
import jmotor.util.ObjectUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 12-4-11
 *
 * @author Andy.Ai
 */
public class AnnotationEntityMapping implements EntityMappingManager {
    private static final Logger logger = LoggerManager.getLogger(AnnotationEntityMapping.class);
    private Cache entityCache = CacheManager.getCache("entityCache");

    @Override
    public TableMeta getEntity(Object entity) {
        Class<?> clazz = ObjectUtils.getClass(entity);
        TableMeta table = entityCache.get(getEntityKey(clazz));
        if (table == null) {
            table = registerEntity(clazz);
        }
        return table;
    }

    private void parseTable(TableMeta table, Class<?> entityClass) {
        table.setTableName(getEntityName(entityClass));
        Table _table = entityClass.getAnnotation(Table.class);
        if (_table != null) {
            table.setTableName(_table.name());
            table.setSchema(_table.schema());
            table.setCatalog(_table.catalog());
            UniqueConstraint[] uniqueConstraints = _table.uniqueConstraints();
            if (CollectionUtils.isNotEmpty(uniqueConstraints)) {
                List<String> columns = new ArrayList<String>(uniqueConstraints.length);
                for (UniqueConstraint uniqueConstraint : uniqueConstraints) {
                    String[] _columns = uniqueConstraint.columnNames();
                    if (CollectionUtils.isNotEmpty(_columns)) {
                        Collections.addAll(columns, _columns);
                    }
                }
                table.setUniqueConstraints(columns.toArray(new String[columns.size()]));
            }
        }
    }

    private void parseColumn(TableMeta table, Class<?> entityClass) throws IntrospectionException {
        PropertyDescriptor[] propertyDescriptors = ClassUtils.getDeclaredPropertyDescriptors(entityClass);
        if (CollectionUtils.isNotEmpty(propertyDescriptors)) {
            List<ColumnMeta> columns = new ArrayList<ColumnMeta>(propertyDescriptors.length);
            List<String> primaryKeys = new ArrayList<String>(propertyDescriptors.length);
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method getter = propertyDescriptor.getReadMethod();
                Transient _transient = getter.getAnnotation(Transient.class);
                if (_transient != null) {
                    continue;
                }
                String columnName = propertyDescriptor.getName();
                Column column = getter.getAnnotation(Column.class);
                if (column != null) {
                    columnName = column.name();
                }
                ColumnMeta _column = new ColumnMeta();
                _column.setColumnName(columnName);
                _column.setPropertyName(propertyDescriptor.getName());
                Id id = getter.getAnnotation(Id.class);
                if (id != null) {
                    primaryKeys.add(columnName);
                }
                GeneratedValue generatedValue = getter.getAnnotation(GeneratedValue.class);
                if (generatedValue != null) {
                    _column.setValueGenerator(generatedValue.generator());
                }
                columns.add(_column);
            }
            if (CollectionUtils.isNotEmpty(primaryKeys)) {
                table.setPrimaryKeys(primaryKeys.toArray(new String[primaryKeys.size()]));
            }
            if (CollectionUtils.isNotEmpty(columns)) {
                table.setColumns(columns.toArray(new ColumnMeta[columns.size()]));
            }
        }
    }

    private String getEntityName(Class<?> clazz) {
        Entity _entity = clazz.getAnnotation(Entity.class);
        if (_entity != null) {
            return _entity.name();
        }
        return null;
    }

    private String getEntityKey(Class<?> entityClass) {
        String entityKey = getEntityName(entityClass);
        if (entityKey == null) {
            entityKey = entityClass.getName();
        }
        return entityKey;
    }

    @Override
    public TableMeta getEntity(String entityName) {
        return entityCache.get(entityName);
    }

    @Override
    public TableMeta registerEntity(Class<?> entityClass) {
        String entityKey = getEntityKey(entityClass);
        if (entityCache.containsKey(entityKey)) {
            throw new EntityExistsException("Entity has exists:" + entityKey);
        }
        TableMeta table = new TableMeta();
        parseTable(table, entityClass);
        try {
            parseColumn(table, entityClass);
        } catch (IntrospectionException e) {
            logger.error("Can't get entity's columns", e);
            throw new EntityMappingException(e);
        }
        entityCache.put(entityKey, table);
        return table;
    }

    @Override
    public boolean exists(String name) {
        return entityCache.containsKey(name);
    }

    @Override
    public boolean exists(Object entity) {
        return exists(getEntityKey(entity.getClass()));
    }
}
