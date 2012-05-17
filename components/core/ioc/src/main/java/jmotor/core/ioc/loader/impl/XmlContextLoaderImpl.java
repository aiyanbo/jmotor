package jmotor.core.ioc.loader.impl;

import jmotor.core.ioc.loader.XmlContextLoader;
import jmotor.core.ioc.exception.ContextLoaderException;
import jmotor.util.StreamUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * Component:
 * Description:
 * Date: 11-8-19
 *
 * @author Andy.Ai
 */
public class XmlContextLoaderImpl implements XmlContextLoader {
    private SAXReader reader = new SAXReader();

    public Document loadBeanDefinitions(String path) throws ContextLoaderException {
        return loadBeanDefinitions(StreamUtils.getStream4ClassPath(path));
    }

    public Document loadBeanDefinitions(File file) throws ContextLoaderException {
        try {
            return reader.read(file);
        } catch (DocumentException e) {
            throw new ContextLoaderException(e);
        }
    }

    public Document loadBeanDefinitions(URL url) throws ContextLoaderException {
        try {
            return reader.read(url);
        } catch (DocumentException e) {
            throw new ContextLoaderException(e);
        }
    }

    public Document loadBeanDefinitions(InputStream inputStream) throws ContextLoaderException {
        try {
            return reader.read(inputStream);
        } catch (DocumentException e) {
            throw new ContextLoaderException(e);
        }
    }
}
