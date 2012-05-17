package jmotor.core.ioc.converter;

import jmotor.core.ioc.config.EntryWrapperConfiguration;

/**
 * Component:
 * Description:
 * Date: 11-11-19
 *
 * @author Andy.Ai
 */
public interface EntryValueConverter {
    Object convert(EntryWrapperConfiguration entryWrapperConfiguration);
}
