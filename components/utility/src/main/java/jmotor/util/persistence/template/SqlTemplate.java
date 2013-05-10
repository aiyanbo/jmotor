package jmotor.util.persistence.template;

/**
 * Component:
 * Description:
 * Date: 12-11-6
 *
 * @author Andy.Ai
 */
public interface SqlTemplate {
    String TABLE_NAME_SYMBOL = "${tableName}";
    String VALUES_PLACEHOLDER_SYMBOL = "${valuesPlaceholder}";
    String COLUMNS_SYMBOL = "${columns}";
    String CONDITIONS_SYMBOL = "${conditions}";
    String SETTINGS_SYMBOL = "${settings}";
    String INSERT = "insert into ${tableName}(${columns}) values(${valuesPlaceholder})";
    String DELETE = "delete from ${tableName} where ${conditions}";
    String UPDATE = "update ${tableName} set ${settings} where ${conditions}";
}
