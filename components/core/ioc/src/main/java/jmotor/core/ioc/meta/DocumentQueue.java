package jmotor.core.ioc.meta;

import jmotor.core.ioc.type.ResourceType;
import org.dom4j.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * Component:
 * Description:
 * Date: 11-8-23
 *
 * @author Andy.Ai
 */
public class DocumentQueue implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private String path;
    private ResourceType type;
    private Document document;
    private List<Properties> resources;

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

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public List<Properties> getResources() {
        return resources;
    }

    public void setResources(List<Properties> resources) {
        this.resources = resources;
    }
}
