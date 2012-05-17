package jmotor.core.ioc.meta;

import jmotor.core.ioc.type.ContextType;
import jmotor.core.ioc.type.ResourceType;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 11-10-13
 *
 * @author Andy.Ai
 */
public class Context implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String path;
    private ResourceType type;
    private InputStream data;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public InputStream getData() {
        return data;
    }

    public void setData(InputStream data) {
        this.data = data;
    }
}
