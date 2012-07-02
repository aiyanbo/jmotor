package jmotor.core.ioc.converter.impl;

import jmotor.core.ioc.config.EntryConfiguration;
import jmotor.core.ioc.config.EntryWrapperConfiguration;
import jmotor.core.ioc.converter.EntryValueConverter;
import jmotor.core.ioc.exception.PropertyCreateException;
import jmotor.util.ClassUtils;
import jmotor.util.converter.SimpleValueConverter;

import java.util.Collection;

/**
 * Component:
 * Description:
 * Date: 11-11-19
 *
 * @author Andy.Ai
 */
public class CollectionConverterImpl<E extends Object> implements EntryValueConverter {

    @SuppressWarnings("unchecked")
    public Object convert(EntryWrapperConfiguration entryWrapperConfiguration) {
        try {
            Collection<E> collection = (Collection<E>) ClassUtils.newInstance(entryWrapperConfiguration.getType());
            E entryType = (E) ClassUtils.loadClass(entryWrapperConfiguration.getValueType());
            for (EntryConfiguration entryConfiguration : entryWrapperConfiguration.getEntries()) {
                Object entryValue = SimpleValueConverter.convert(entryType.getClass(), entryConfiguration.getValue());
                collection.add((E) entryValue);
            }
            return collection;
        } catch (Exception e) {
            throw new PropertyCreateException(e);
        }
    }
}
