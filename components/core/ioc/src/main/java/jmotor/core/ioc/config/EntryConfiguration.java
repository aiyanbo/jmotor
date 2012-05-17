package jmotor.core.ioc.config;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 11-11-14
 *
 * @author Andy.Ai
 */
public class EntryConfiguration implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String key;
    private String value;
    private String ref;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
