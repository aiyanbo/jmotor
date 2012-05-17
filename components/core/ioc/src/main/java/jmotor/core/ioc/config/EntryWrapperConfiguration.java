package jmotor.core.ioc.config;

import java.io.Serializable;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 11-11-14
 *
 * @author Andy.Ai
 */
public class EntryWrapperConfiguration implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String id;
    private String category;
    private String type;
    private boolean singleton;
    private String keyType;
    private String valueType;
    private List<EntryConfiguration> entries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public List<EntryConfiguration> getEntries() {
        return entries;
    }

    public void setEntries(List<EntryConfiguration> entries) {
        this.entries = entries;
    }
}
