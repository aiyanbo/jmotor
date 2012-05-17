package jmotor.core.ioc.config;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 11-8-17
 *
 * @author Andy.Ai
 */
public class ElementConfiguration extends GlobalConfiguration {
    private String id;
    private String clazz;
    private String parent;
    private boolean isAbstract;
    private List<PropertyConfiguration> properties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean anAbstract) {
        isAbstract = anAbstract;
    }

    public List<PropertyConfiguration> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyConfiguration> properties) {
        this.properties = properties;
    }
}
