package jmotor.core.ioc.meta;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 11-8-19
 *
 * @author Andy.Ai
 */
public class Element implements Serializable{
    private static final long serialVersionUID = -6849794470754667710L;
    private String key;
    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
