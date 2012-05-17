package jmotor.core.ioc.loader.impl;

import jmotor.core.ioc.context.ApplicationContextConstant;
import jmotor.core.ioc.exception.ContextLoaderException;
import jmotor.core.ioc.loader.ContextLoader;
import jmotor.core.ioc.loader.ContextLoaderAdapter;
import jmotor.core.ioc.meta.Context;
import jmotor.core.ioc.type.ResourceType;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Component:
 * Description:
 * Date: 11-8-23
 *
 * @author Andy.Ai
 */
public class ContextLoaderAdapterImpl implements ContextLoaderAdapter {
    private ContextLoader contextLoader;

    public Context loadContext(String src) throws ContextLoaderException {
        int index = src.indexOf(':');
        if (index < 0) {
            index = src.indexOf('@');
        }
        ResourceType resourceType = getResourceType(src, index);
        String path = src.substring(index + 1);
        Context context = new Context();
        context.setType(resourceType);
        context.setPath(path);
        context.setData(getContextData(resourceType, path));
        return context;
    }

    private InputStream getContextData(ResourceType resourceType, String path) throws ContextLoaderException {
        InputStream inputStream = null;
        switch (resourceType) {
            case CLASS_PATH:
                inputStream = contextLoader.loadContext(path);
                break;
            case FILE_SYSTEM:
                inputStream = contextLoader.loadContext(new File(path));
                break;
            case URL:
                try {
                    inputStream = contextLoader.loadContext(new URL(path));
                } catch (MalformedURLException e) {
                    throw new ContextLoaderException(e);
                }
                break;
            default:
                break;
        }
        return inputStream;
    }

    private ResourceType getResourceType(String src, int index) {
        ResourceType resourceType = ResourceType.CLASS_PATH;
        if (index > 0) {
            String type = src.substring(0, index);
            if (ApplicationContextConstant.CLASS_PATH.equalsIgnoreCase(type)) {
                resourceType = ResourceType.CLASS_PATH;
            } else if (ApplicationContextConstant.FILE_SYSTEM.equalsIgnoreCase(type)) {
                resourceType = ResourceType.FILE_SYSTEM;
            } else if (ApplicationContextConstant.URL.equalsIgnoreCase(type)) {
                resourceType = ResourceType.URL;
            }
        }
        return resourceType;
    }

    public void setContextLoader(ContextLoader contextLoader) {
        this.contextLoader = contextLoader;
    }
}
