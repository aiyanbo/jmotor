package jmotor.core.i18n;


import jmotor.util.i18n.I18nProperties;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.Properties;

/**
 * Component:
 * Description:
 * Date: 12-3-10
 *
 * @author Andy.Ai
 */
public class ResourceBundleTest extends TestCase {
    public void testGetI18nValue() throws IOException {
        Properties properties = new I18nResourceProperties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("resource_zh_CN.properties"));
        System.out.println(properties.getProperty("name"));
        System.out.println(properties.getProperty("next"));
        System.out.println(properties.getProperty("title"));
        Properties properties1 = new I18nProperties();
        properties1.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("resource_zh_CN.properties"));
        System.out.println(properties1.getProperty("name"));
        System.out.println(properties1.getProperty("next"));
    }
}

