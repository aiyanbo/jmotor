package jmotor.core.ioc.pool;

import jmotor.core.ioc.context.BeanConfigurationContext;
import jmotor.core.ioc.repository.Repository;

import java.util.Collection;

/**
 * Component:
 * Description:
 * Date: 11-8-22
 *
 * @author Andy.Ai
 */
public interface BeanConfigurationPool extends Repository {
    String get(String key);

    void put(BeanConfigurationContext beanConfigurationContext);

    void put(String key, String context);

    void putAll(Collection<BeanConfigurationContext> beanConfigurationContexts);
}
