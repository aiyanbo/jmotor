package jmotor.util.persistence.parser.impl;

import jmotor.util.ClassUtils;
import jmotor.util.CollectionUtils;
import jmotor.util.StringUtils;
import jmotor.util.exception.EntityParseException;
import jmotor.util.persistence.parser.EntityParserCallback;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 12-11-7
 *
 * @author Andy.Ai
 */
public class JPAEntityParserCallback implements EntityParserCallback {
    @Override
    public String[] filter(Class<?> entityClass) throws EntityParseException {
        try {
            PropertyDescriptor[] propertyDescriptors = ClassUtils.getPropertyDescriptors(entityClass);
            List<String> propertyNames = new ArrayList<String>(propertyDescriptors.length);
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Method reader = propertyDescriptor.getReadMethod();
                Transient transientSymbol = reader.getAnnotation(Transient.class);
                if (transientSymbol == null) {
                    Field field = ClassUtils.getField(entityClass, propertyDescriptor.getName());
                    if (field != null) {
                        transientSymbol = field.getAnnotation(Transient.class);
                    }
                }
                if (transientSymbol != null || !ClassUtils.isPrimitiveClass(reader.getReturnType())) {
                    propertyNames.add(propertyDescriptor.getName());
                }
            }
            return propertyNames.toArray(new String[propertyNames.size()]);
        } catch (Exception e) {
            throw new EntityParseException(e.getMessage(), e);
        }
    }

    @Override
    public String getColumnName(String propertyName, Class<?> entityClass) throws EntityParseException {
        String databaseColumnName = null;
        Field field;
        try {
            field = ClassUtils.getField(entityClass, propertyName);
        } catch (NoSuchFieldException e) {
            throw new EntityParseException(e.getLocalizedMessage(), e);
        }
        if (field != null) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && StringUtils.isNotBlank(column.name())) {
                databaseColumnName = column.name();
            }
        }
        if (StringUtils.isBlank(databaseColumnName)) {
            databaseColumnName = StringUtils.nameOfDatabase(propertyName);
        }
        return databaseColumnName;
    }

    @Override
    public String[] getPrimaryKeys(Class<?> entityClass) throws EntityParseException {
        Table tableSymbol = entityClass.getAnnotation(Table.class);
        if (tableSymbol != null) {
            UniqueConstraint[] uniqueConstraints = tableSymbol.uniqueConstraints();
            if (CollectionUtils.isNotEmpty(uniqueConstraints)) {
                List<String> uniqueNames = new ArrayList<String>(5);
                for (UniqueConstraint uniqueConstraint : uniqueConstraints) {
                    Collections.addAll(uniqueNames, uniqueConstraint.columnNames());
                }
                return uniqueNames.toArray(new String[uniqueNames.size()]);
            }
        }
        return null;
    }

    @Override
    public String getTableName(Class<?> entityClass) throws EntityParseException {
        String entityName = null;
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null) {
            entityName = tableAnnotation.name();
        }
        if (StringUtils.isBlank(entityName)) {
            Entity entityAnnotation = entityClass.getAnnotation(Entity.class);
            if (entityAnnotation != null && StringUtils.isNotBlank(entityAnnotation.name())) {
                entityName = entityAnnotation.name();
            }
        }
        return StringUtils.isNotBlank(entityName) ? entityName : entityClass.getSimpleName();
    }

    @Override
    public String[] appendColumns(Class<?> entityClass) throws EntityParseException {
        return new String[0];
    }
}
