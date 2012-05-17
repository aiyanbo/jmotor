package jmotor.core.ioc;

import jmotor.core.ioc.config.ElementConfiguration;

/**
 * Component:
 * Description:
 * Date: 11-8-17
 *
 * @author Andy.Ai
 */
public interface BeanBusManager {
    <T> T getBean(String id);

    <T> T createBean(ElementConfiguration elementConfiguration);

    void autoWire(Object bean);
}
