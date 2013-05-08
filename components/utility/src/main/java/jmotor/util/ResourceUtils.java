package jmotor.util;

import jmotor.util.dto.ResourceDto;
import jmotor.util.type.ResourceType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Component:
 * Description:
 * Date: 12-6-22
 *
 * @author Andy.Ai
 */
public class ResourceUtils {
    private ResourceUtils() {
    }

    public static ResourceDto getResource(String path) throws IOException {
        int index = path.indexOf(':');
        if (index < 0) {
            index = path.indexOf('@');
        }
        String type = "";
        String _path = path;
        if (index > 0) {
            type = path.substring(0, index);
            _path = path.substring(index + 1);
        }
        InputStream inputStream;
        ResourceType resourceType;
        if (ResourceType.CLASS_PATH.toString().equalsIgnoreCase(type)) {
            inputStream = StreamUtils.getStream4ClassPath(_path);
            resourceType = ResourceType.CLASS_PATH;
        } else if (ResourceType.FILE_SYSTEM.toString().equalsIgnoreCase(type)) {
            inputStream = StreamUtils.getStream4FileSystem(_path);
            resourceType = ResourceType.FILE_SYSTEM;
        } else if (ResourceType.URL.toString().equalsIgnoreCase(type)) {
            inputStream = StreamUtils.getStream4Url(_path);
            resourceType = ResourceType.URL;
        } else {
            inputStream = StreamUtils.getStream4ClassPath(_path);
            resourceType = ResourceType.CLASS_PATH;
        }
        ResourceDto resource = new ResourceDto();
        resource.setData(inputStream);
        resource.setPath(_path);
        resource.setType(resourceType.name());
        return resource;
    }

    public static Properties loadProperties(InputStream inputStream) throws IOException {
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } finally {
            inputStream.close();
        }
    }

    public static Properties loadProperties(String path) throws IOException {
        ResourceDto resource = getResource(path);
        return loadProperties(resource.getData());
    }

    public static Properties loadProperties(ClassLoader classLoader, String name) throws IOException {
        return loadProperties(name, classLoader, false);
    }

    public static Properties loadProperties(String name, boolean usedFilesystem) throws IOException {
        return loadProperties(name, Thread.currentThread().getContextClassLoader(), usedFilesystem);
    }

    public static Properties loadProperties(String name, ClassLoader classLoader, boolean usedFilesystem) throws IOException {
        InputStream inputStream = null;
        try {
            Properties properties = new Properties();
            if (usedFilesystem) {
                URL resource = classLoader.getResource(name);
                if (resource != null) {
                    inputStream = new FileInputStream(resource.getFile());
                }
            } else {
                inputStream = classLoader.getResourceAsStream(name);
            }
            properties.load(inputStream);
            return properties;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static String getProperty(Properties properties, String key) {
        String value = properties.getProperty(key);
        return StringUtils.trim(value);
    }
}
