package jmotor.core.ioc.loader;

import jmotor.core.ioc.exception.ContextLoaderException;
import org.dom4j.Document;

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
public interface XmlContextLoader {
    Document loadBeanDefinitions(String path) throws ContextLoaderException;

    Document loadBeanDefinitions(File file) throws ContextLoaderException;

    Document loadBeanDefinitions(URL url) throws ContextLoaderException;

    Document loadBeanDefinitions(InputStream inputStream) throws ContextLoaderException;
}
