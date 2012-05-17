package jmotor.core.persistence.meta;

/**
 * Component:
 * Description:
 * Date: 11-10-1
 *
 * @author Andy.Ai
 */
public class CellDataMeta {
    private String name;
    private Object value;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
