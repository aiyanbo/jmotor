package jmotor.core.ioc.factory;

import jmotor.core.ioc.BeanBusManager;
import jmotor.core.ioc.context.ApplicationContext;
import jmotor.core.ioc.exception.BeanBusCreateException;
import jmotor.core.ioc.exception.ContextLoaderException;
import jmotor.core.ioc.impl.BeanBusManagerImpl;
import jmotor.core.ioc.loader.XmlContextLoader;
import jmotor.core.ioc.loader.impl.XmlContextLoaderImpl;
import jmotor.util.StreamUtils;
import org.dom4j.Document;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Component:
 * Description:
 * Date: 11-8-17
 *
 * @author Andy.Ai
 */
public class BeanBusFactory {
    private static BeanBusManager beanBus;

    private BeanBusFactory() {
    }

    public static BeanBusManager createBeanBus() {
        return createBeanBus("/beanbus.xml");
    }

    public static BeanBusManager createBeanBus(String path) {
        InputStream is = StreamUtils.getStream4ClassPath(path);
        BeanBusManager beanBusManager = createBeanBus(is);
        try {
            is.close();
        } catch (IOException e) {
            throw new BeanBusCreateException("BeanBus can't create", e);
        }
        return beanBusManager;
    }

    public static BeanBusManager createBeanBus(URL url) {
        try {
            InputStream is = url.openStream();
            BeanBusManager beanBusManager = createBeanBus(is);
            is.close();
            return beanBusManager;
        } catch (IOException e) {
            throw new BeanBusCreateException("BeanBus can't create", e);
        }
    }

    public static BeanBusManager createBeanBus(InputStream inputStream) {
        XmlContextLoader xmlContextLoader = new XmlContextLoaderImpl();
        Document document;
        try {
            document = xmlContextLoader.loadBeanDefinitions(inputStream);
        } catch (ContextLoaderException e) {
            throw new BeanBusCreateException("BeanBus can't create", e);
        }
        BeanBusManagerImpl beanBusManager = new BeanBusManagerImpl();
        beanBusManager.setContextParser(ApplicationContext.getContextParser());
        beanBusManager.setContextQueueParser(ApplicationContext.getContextQueueParser());
        beanBusManager.initialization(document);
        beanBus = beanBusManager;
        return beanBus;
    }

    public static BeanBusManager getBeanBus() {
        return beanBus;
    }
}
