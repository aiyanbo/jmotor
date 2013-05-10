package jmotor.util.persistence.impl;

import jmotor.util.CollectionUtils;
import jmotor.util.StringUtils;
import jmotor.util.exception.SqlGenerateException;
import jmotor.util.persistence.SqlGenerator;
import jmotor.util.persistence.dto.EntityMapper;
import jmotor.util.persistence.dto.PropertyMapper;
import jmotor.util.persistence.dto.SqlStatement;
import jmotor.util.persistence.parser.EntityParser;
import jmotor.util.persistence.parser.EntityParserCallback;
import jmotor.util.persistence.parser.impl.EntityParserImpl;
import jmotor.util.persistence.parser.impl.JPAEntityParserCallback;
import jmotor.util.persistence.template.SqlTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Component:
 * Description:
 * Date: 12-11-6
 *
 * @author Andy.Ai
 */
public class SqlGeneratorImpl implements SqlGenerator {
    private static EntityParser DEFAULT_ENTITY_PARSER;

    static {
        EntityParserImpl entityParser = new EntityParserImpl();
        entityParser.setCallback(new JPAEntityParserCallback());
        DEFAULT_ENTITY_PARSER = entityParser;
    }

    @Override
    public SqlStatement generateInsertSql(Class<?> entityClass) {
        return generateInsertSql(entityClass, DEFAULT_ENTITY_PARSER);
    }

    @Override
    public SqlStatement generateUpdateSql(Class<?> entityClass) {
        return generateUpdateSql(entityClass, DEFAULT_ENTITY_PARSER);
    }

    @Override
    public SqlStatement generateDeleteSql(Class<?> entityClass) {
        return generateDeleteSql(entityClass, DEFAULT_ENTITY_PARSER);
    }

    @Override
    public SqlStatement generateInsertSql(Class<?> entityClass, EntityParserCallback callback) {
        EntityParserImpl entityParser = new EntityParserImpl();
        entityParser.setCallback(callback);
        return generateInsertSql(entityClass, entityParser);
    }

    @Override
    public SqlStatement generateUpdateSql(Class<?> entityClass, EntityParserCallback callback) {
        EntityParserImpl entityParser = new EntityParserImpl();
        entityParser.setCallback(callback);
        return generateUpdateSql(entityClass, entityParser);
    }

    @Override
    public SqlStatement generateDeleteSql(Class<?> entityClass, EntityParserCallback callback) {
        EntityParserImpl entityParser = new EntityParserImpl();
        entityParser.setCallback(callback);
        return generateDeleteSql(entityClass, entityParser);
    }

    private SqlStatement generateInsertSql(Class<?> entityClass, EntityParser parser) {
        try {
            EntityMapper entityMapper = parser.getEntityMapper(entityClass);
            PropertyMapper propertyNameMapper = entityMapper.getPropertyMapper();
            SqlStatement sqlStatement = new SqlStatement();
            sqlStatement.setPropertyMapper(propertyNameMapper);
            String columns = StringUtils.join(propertyNameMapper.valueArray(), StringUtils.COMMA);
            String valuesPlaceholder = StringUtils.repeat(StringUtils.QUESTION_MARK, StringUtils.COMMA,
                    propertyNameMapper.size());
            String sql = SqlTemplate.INSERT.replace(SqlTemplate.TABLE_NAME_SYMBOL, entityMapper.getTableName());
            sql = sql.replace(SqlTemplate.COLUMNS_SYMBOL, columns);
            sql = sql.replace(SqlTemplate.VALUES_PLACEHOLDER_SYMBOL, valuesPlaceholder);
            sqlStatement.setSql(sql);
            return sqlStatement;
        } catch (Exception e) {
            throw new SqlGenerateException(e.getMessage(), e);
        }
    }

    private SqlStatement generateUpdateSql(Class<?> entityClass, EntityParser parser) {
        try {
            EntityMapper entityMapper = parser.getEntityMapper(entityClass);
            SqlStatement sqlStatement = new SqlStatement();
            PropertyMapper propertyMapper = new PropertyMapper();
            List<String> identityProperties = getIdentities(entityMapper);
            PropertyMapper entityPropertyMapper = entityMapper.getPropertyMapper();
            List<String> properties = entityPropertyMapper.keyList();
            properties.removeAll(identityProperties);
            String settings = buildSettings(entityPropertyMapper, propertyMapper, properties);
            String conditions = buildConditions(entityPropertyMapper, propertyMapper, identityProperties);
            String sql = SqlTemplate.UPDATE.replace(SqlTemplate.TABLE_NAME_SYMBOL, entityMapper.getTableName());
            sql = sql.replace(SqlTemplate.SETTINGS_SYMBOL, settings);
            sql = sql.replace(SqlTemplate.CONDITIONS_SYMBOL, conditions);
            sqlStatement.setSql(sql);
            sqlStatement.setPropertyMapper(propertyMapper);
            return sqlStatement;
        } catch (Exception e) {
            throw new SqlGenerateException(e.getMessage(), e);
        }
    }

    private SqlStatement generateDeleteSql(Class<?> entityClass, EntityParser parser) {
        try {
            EntityMapper entityMapper = parser.getEntityMapper(entityClass);
            SqlStatement sqlStatement = new SqlStatement();
            PropertyMapper propertyMapper = new PropertyMapper();
            List<String> identityProperties = getIdentities(entityMapper);
            String conditions = buildConditions(entityMapper.getPropertyMapper(), propertyMapper, identityProperties);
            String sql = SqlTemplate.DELETE.replace(SqlTemplate.TABLE_NAME_SYMBOL, entityMapper.getTableName());
            sql = sql.replace(SqlTemplate.CONDITIONS_SYMBOL, conditions);
            sqlStatement.setSql(sql);
            sqlStatement.setPropertyMapper(propertyMapper);
            return sqlStatement;
        } catch (Exception e) {
            throw new SqlGenerateException(e.getMessage(), e);
        }
    }

    private List<String> getIdentities(EntityMapper entityMapper) {
        Set<String> identityProperties = new HashSet<String>(5);
        if (StringUtils.isNotBlank(entityMapper.getIdentityName())) {
            Collections.addAll(identityProperties, entityMapper.getIdentityName());
        }
        if (CollectionUtils.isNotEmpty(entityMapper.getUniqueNames())) {
            Collections.addAll(identityProperties, entityMapper.getUniqueNames());
        }
        List<String> result = new ArrayList<String>(identityProperties.size());
        result.addAll(identityProperties);
        return result;
    }

    private String buildConditions(PropertyMapper entityPropertyMapper, PropertyMapper propertyMapper, List<String> identityProperties) {
        StringBuilder conditionsBuilder = new StringBuilder(identityProperties.size() * 15);
        for (int i = 0; i < identityProperties.size(); i++) {
            String identityProperty = identityProperties.get(i);
            String identityColumnName = entityPropertyMapper.get(identityProperty);
            conditionsBuilder.append(identityColumnName);
            conditionsBuilder.append(" = ?");
            if (i < identityProperties.size() - 1) {
                conditionsBuilder.append(" and ");
            }
            propertyMapper.put(identityProperty, identityColumnName);
        }
        return conditionsBuilder.toString();
    }

    private String buildSettings(PropertyMapper entityPropertyMapper, PropertyMapper propertyMapper, List<String> properties) {
        StringBuilder settingsBuilder = new StringBuilder(properties.size() * 7);
        for (int i = 0; i < properties.size(); i++) {
            String propertyName = properties.get(i);
            String columnName = entityPropertyMapper.get(propertyName);
            propertyMapper.put(propertyName, columnName);
            settingsBuilder.append(columnName);
            settingsBuilder.append(" = ?");
            if (i < properties.size() - 1) {
                settingsBuilder.append(", ");
            }
        }
        return settingsBuilder.toString();
    }

}
