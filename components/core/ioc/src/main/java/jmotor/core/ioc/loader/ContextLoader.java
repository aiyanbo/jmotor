package jmotor.core.ioc.loader;

import jmotor.core.ioc.exception.ContextLoaderException;
import org.dom4j.Document;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Component:
 * Description:
 * Date: 11-10-11
 *
 * @author Andy.Ai
 */
public interface ContextLoader {
    InputStream loadContext(String path) throws ContextLoaderException;

    InputStream loadContext(File file) throws ContextLoaderException;

    InputStream loadContext(URL url) throws ContextLoaderException;
}
