package jmotor.core.persistence.meta;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 12-4-11
 *
 * @author Andy.Ai
 */
public class TableMeta implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String tableName;
    private String catalog;
    private String schema;
    private String[] uniqueConstraints;
    private String[] primaryKeys;
    private ColumnMeta[] columns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String[] getUniqueConstraints() {
        return uniqueConstraints;
    }

    public void setUniqueConstraints(String[] uniqueConstraints) {
        this.uniqueConstraints = uniqueConstraints;
    }

    public String[] getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(String[] primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public ColumnMeta[] getColumns() {
        return columns;
    }

    public void setColumns(ColumnMeta[] columns) {
        this.columns = columns;
    }
}
