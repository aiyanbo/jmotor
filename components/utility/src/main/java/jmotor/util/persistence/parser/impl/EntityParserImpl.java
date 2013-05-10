package jmotor.util.persistence.parser.impl;

import jmotor.util.ClassUtils;
import jmotor.util.CollectionUtils;
import jmotor.util.StringUtils;
import jmotor.util.exception.EntityParseException;
import jmotor.util.persistence.dto.EntityMapper;
import jmotor.util.persistence.dto.PropertyMapper;
import jmotor.util.persistence.parser.EntityParser;
import jmotor.util.persistence.parser.EntityParserCallback;

import javax.persistence.Id;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Component:
 * Description:
 * Date: 12-11-7
 *
 * @author Andy.Ai
 */
public class EntityParserImpl implements EntityParser {
    private EntityParserCallback callback;

    @Override
    public EntityMapper getEntityMapper(Class<?> entityClass) throws EntityParseException {
        try {
            PropertyDescriptor[] propertyDescriptors = ClassUtils.getPropertyDescriptors(entityClass);
            String[] filters = null;
            String tableName = entityClass.getName();
            String[] appendColumns = null;
            String[] uniqueNames = null;
            if (null != callback) {
                filters = callback.filter(entityClass);
                tableName = callback.getTableName(entityClass);
                appendColumns = callback.appendColumns(entityClass);
                uniqueNames = callback.getPrimaryKeys(entityClass);
            }
            String identityName = getIdentityName(entityClass, propertyDescriptors);
            PropertyMapper propertyMapper = new PropertyMapper();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                boolean included = true;
                String propertyName = propertyDescriptor.getName();
                if (CollectionUtils.isNotEmpty(filters)) {
                    for (String filter : filters) {
                        if (propertyName.equals(filter)) {
                            included = false;
                            break;
                        }
                    }
                }
                if (included) {
                    String columnName = StringUtils.nameOfDatabase(propertyName);
                    if (callback != null) {
                        callback.getColumnName(propertyName, entityClass);
                    }
                    propertyMapper.put(propertyName, columnName);
                }
            }
            if (CollectionUtils.isNotEmpty(appendColumns)) {
                for (String column : appendColumns) {
                    propertyMapper.put(column, column);
                }
            }
            EntityMapper entityMapper = new EntityMapper();
            entityMapper.setTableName(tableName);
            entityMapper.setIdentityName(identityName);
            entityMapper.setUniqueNames(uniqueNames);
            entityMapper.setPropertyMapper(propertyMapper);
            return entityMapper;
        } catch (Exception e) {
            throw new EntityParseException(e.getMessage(), e);
        }
    }

    private String getIdentityName(Class<?> entityClass, PropertyDescriptor[] propertyDescriptors) throws NoSuchFieldException {
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Method reader = propertyDescriptor.getReadMethod();
            Id identity = reader.getAnnotation(Id.class);
            if (identity == null) {
                Field field = ClassUtils.getField(entityClass, propertyDescriptor.getName());
                if (field != null) {
                    identity = field.getAnnotation(Id.class);
                }
            }
            if (identity != null) {
                return propertyDescriptor.getName();
            }
        }
        return null;
    }

    public void setCallback(EntityParserCallback callback) {
        this.callback = callback;
    }
}
