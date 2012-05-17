package jmotor.core.ioc.converter.impl;

import jmotor.core.ioc.config.EntryWrapperConfiguration;
import jmotor.core.ioc.config.EntryConfiguration;
import jmotor.core.ioc.converter.EntryValueConverter;
import jmotor.core.ioc.exception.PropertyCreateException;
import jmotor.util.ClassUtils;
import jmotor.util.StringUtils;
import jmotor.util.converter.SimpleValueConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 11-11-20
 *
 * @author Andy.Ai
 */
public class ArrayConverterImpl<E extends Object> implements EntryValueConverter {
    public Object convert(EntryWrapperConfiguration entryWrapperConfiguration) {
        try {
            List<E> list = new ArrayList<E>(entryWrapperConfiguration.getEntries().size());
            E valueType = (E) ClassUtils.loadClass(entryWrapperConfiguration.getValueType());
            for (EntryConfiguration entryConfiguration : entryWrapperConfiguration.getEntries()) {
                if (StringUtils.isNotBlank(entryConfiguration.getValue())) {
                    Object value = SimpleValueConverter.convert(valueType.getClass(), entryConfiguration.getValue());
                    list.add((E) value);
                }
            }
            return list.toArray(new Object[list.size()]);
        } catch (Exception e) {
            throw new PropertyCreateException(e);
        }
    }
}
