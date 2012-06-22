package jmotor.util.dto;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 12-6-22
 *
 * @author Andy.Ai
 */
public class ResourceDto implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String path;
    private String type;
    private InputStream data;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InputStream getData() {
        return data;
    }

    public void setData(InputStream data) {
        this.data = data;
    }
}
