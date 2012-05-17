package jmotor.core.ioc.pool;

import jmotor.core.ioc.config.ElementConfiguration;
import jmotor.core.ioc.repository.Repository;

import java.util.Collection;

/**
 * Component:
 * Description:
 * Date: 11-8-19
 *
 * @author Andy.Ai
 */
public interface ElementConfigurationPool extends Repository {
    void put(ElementConfiguration elementConfiguration);

    void putAll(Collection<ElementConfiguration> elementConfigurations);

    ElementConfiguration get(String key);

    ElementConfiguration getByClassName(String className);
}
