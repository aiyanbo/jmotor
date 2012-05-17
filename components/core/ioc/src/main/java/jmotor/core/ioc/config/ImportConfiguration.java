package jmotor.core.ioc.config;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 11-8-20
 *
 * @author Andy.Ai
 */
public class ImportConfiguration implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String type;
    private String path;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
