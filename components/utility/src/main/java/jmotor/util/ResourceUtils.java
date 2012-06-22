package jmotor.util;

import jmotor.util.dto.ResourceDto;
import jmotor.util.type.ResourceType;

import java.io.IOException;
import java.io.InputStream;

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
            path.substring(0, index);
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
}
