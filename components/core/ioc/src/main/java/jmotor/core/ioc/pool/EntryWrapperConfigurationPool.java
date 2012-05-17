package jmotor.core.ioc.pool;

import jmotor.core.ioc.config.EntryWrapperConfiguration;
import jmotor.core.ioc.repository.Repository;

import java.util.Collection;

/**
 * Component:
 * Description:
 * Date: 11-11-14
 *
 * @author Andy.Ai
 */
public interface EntryWrapperConfigurationPool extends Repository {
    void put(EntryWrapperConfiguration entryWrapperConfiguration);

    void putAll(Collection<EntryWrapperConfiguration> entryWrapperConfigurations);

    EntryWrapperConfiguration get(String key);
}
