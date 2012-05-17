package jmotor.core.ioc.config;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 11-8-17
 *
 * @author Andy.Ai
 */
public class PropertyConfiguration implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String name;
    private String value;
    private String ref;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
