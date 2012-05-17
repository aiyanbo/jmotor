package jmotor.core.persistence.generator.impl;

import jmotor.core.log.Logger;
import jmotor.core.log.LoggerManager;
import jmotor.core.persistence.EntityMappingManager;
import jmotor.core.persistence.generator.SqlGenerator;
import jmotor.core.persistence.meta.ColumnMeta;
import jmotor.core.persistence.meta.TableMeta;
import jmotor.util.CollectionUtils;
import jmotor.util.ObjectUtils;
import jmotor.util.StringUtils;

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
public class SqlGeneratorDefaultImpl implements SqlGenerator {
    private static final Logger logger = LoggerManager.getLogger(SqlGeneratorDefaultImpl.class);
    private EntityMappingManager entityMappingManager;

    @Override
    public String generateInsertSql(Object entity) {
        ensureEntity(entity);
        TableMeta table = entityMappingManager.getEntity(entity);
        ColumnMeta[] columns = table.getColumns();
        List<String> columnNames = new ArrayList<String>(columns.length);
        for (ColumnMeta column : columns) {
            Object value = ObjectUtils.getPropertyValue(entity, column.getPropertyName());
            if (value != null || StringUtils.isNotBlank(column.getValueGenerator())) {
                columnNames.add(column.getColumnName());
            }
        }
        String sql = "insert into ${tableName}(${columnNames}) values(${placeholder})";
        sql = sql.replace("${tableName}", table.getTableName());
        sql = sql.replace("${columnNames}", StringUtils.join(columnNames, ","));
        sql = sql.replace("${placeholder}", StringUtils.join("?", columnNames.size(), ","));
        return sql;
    }

    @Override
    public String generateDeleteSql(Object entity) {
        ensureEntity(entity);
        TableMeta table = entityMappingManager.getEntity(entity);
        ColumnMeta[] columns = table.getColumns();
        List<String> conditions = new ArrayList<String>(columns.length);
        for (ColumnMeta column : columns) {
            Object value = ObjectUtils.getPropertyValue(entity, column.getPropertyName());
            if (value != null) {
                conditions.add(column.getColumnName() + " = ?");
            }
        }
        String sql = "delete from ${tableName} where ${conditions}";
        sql = sql.replace("${tableName}", table.getTableName());
        sql = sql.replace("${conditions}", StringUtils.join(conditions, " and "));
        return sql;
    }

    @Override
    public String generateUpdateSql(Object entity) {
        ensureEntity(entity);
        TableMeta table = entityMappingManager.getEntity(entity);
        List<String> keys = new ArrayList<String>(10);
        if (CollectionUtils.isNotEmpty(table.getPrimaryKeys())) {
            Collections.addAll(keys, table.getPrimaryKeys());
        } else if (CollectionUtils.isNotEmpty(table.getUniqueConstraints())) {
            Collections.addAll(keys, table.getUniqueConstraints());
        }
        if (CollectionUtils.isEmpty(keys)) {
            throw new IllegalArgumentException("Object's keys are undefined");
        }
        ColumnMeta[] columns = table.getColumns();
        List<String> settings = new ArrayList<String>(columns.length);
        for (ColumnMeta column : columns) {
            Object value = ObjectUtils.getPropertyValue(entity, column.getPropertyName());
            if (value != null && !keys.contains(column.getColumnName())) {
                settings.add(column.getColumnName() + " = ?");
            }
        }
        StringBuilder conditions = new StringBuilder(keys.size() * 10);
        int count = keys.size();
        for (int i = 0; i < count; i++) {
            conditions.append(keys.get(i)).append(" = ?");
            if (i < count - 1) {
                conditions.append(" and ");
            }
        }
        String sql = "update ${tableName} set ${settings} where ${conditions}";
        sql = sql.replace("${tableName}", table.getTableName());
        sql = sql.replace("${settings}", StringUtils.join(settings, ","));
        sql = sql.replace("${conditions}", conditions);
        return sql;
    }

    private void ensureEntity(Object entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity is null");
        }
    }

    public void setEntityMappingManager(EntityMappingManager entityMappingManager) {
        this.entityMappingManager = entityMappingManager;
    }
}
