package jmotor.util.persistence.dto;

/**
 * Component:
 * Description:
 * Date: 12-11-6
 *
 * @author Andy.Ai
 */
public class SqlStatement {
    private String sql;
    private PropertyMapper propertyMapper;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public PropertyMapper getPropertyMapper() {
        return propertyMapper;
    }

    public void setPropertyMapper(PropertyMapper propertyMapper) {
        this.propertyMapper = propertyMapper;
    }
}
