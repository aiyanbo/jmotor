package jmotor.core.ioc.loader.impl;

import jmotor.core.ioc.exception.ContextLoaderException;
import jmotor.core.ioc.loader.ContextLoader;
import jmotor.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Component:
 * Description:
 * Date: 11-8-19
 *
 * @author Andy.Ai
 */
public class ContextLoaderImpl implements ContextLoader {
    public InputStream loadContext(String path) throws ContextLoaderException {
        return StreamUtils.getStream4ClassPath(path);
    }

    public InputStream loadContext(File file) throws ContextLoaderException {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ContextLoaderException(file.getAbsolutePath() + "Not found!", e);
        }
    }

    public InputStream loadContext(URL url) throws ContextLoaderException {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new ContextLoaderException(e);
        }
    }
}
