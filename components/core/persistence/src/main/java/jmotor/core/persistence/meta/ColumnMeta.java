package jmotor.core.persistence.meta;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 12-4-11
 *
 * @author Andy.Ai
 */
public class ColumnMeta implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String columnName;
    private String propertyName;
    private String valueGenerator;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getValueGenerator() {
        return valueGenerator;
    }

    public void setValueGenerator(String valueGenerator) {
        this.valueGenerator = valueGenerator;
    }
}
