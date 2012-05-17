package jmotor.core.ioc.pool;

import jmotor.core.ioc.repository.Repository;

import java.util.Map;
import java.util.Properties;

/**
 * Component:
 * Description:
 * Date: 11-11-14
 *
 * @author Andy.Ai
 */
public interface PropertiesPool extends Repository {
    void putAll(Map<String, String> properties);

    void put(Properties properties);

    void put(String key, String value);

    String get(String key);
}
