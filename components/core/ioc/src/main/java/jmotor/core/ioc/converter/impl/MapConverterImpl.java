package jmotor.core.ioc.converter.impl;

import jmotor.core.ioc.config.EntryConfiguration;
import jmotor.core.ioc.config.EntryWrapperConfiguration;
import jmotor.core.ioc.converter.EntryValueConverter;
import jmotor.core.ioc.exception.PropertyCreateException;
import jmotor.util.ClassUtils;
import jmotor.util.converter.SimpleValueConverter;

import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 11-11-19
 *
 * @author Andy.Ai
 */
public class MapConverterImpl<K extends Object, V extends Object> implements EntryValueConverter {

    public Object convert(EntryWrapperConfiguration entryWrapperConfiguration) {
        try {
            Map<K, V> map = (Map<K, V>) ClassUtils.newInstance(entryWrapperConfiguration.getType());
            Class<K> keyType = (Class<K>) ClassUtils.loadClass(entryWrapperConfiguration.getKeyType());
            Class<V> valueType = (Class<V>) ClassUtils.loadClass(entryWrapperConfiguration.getValueType());
            for (EntryConfiguration entryConfiguration : entryWrapperConfiguration.getEntries()) {
                Object key = SimpleValueConverter.convert(keyType, entryConfiguration.getKey());
                Object value = SimpleValueConverter.convert(valueType, entryConfiguration.getValue());
                map.put((K) key, (V) value);
            }
            return map;
        } catch (Exception e) {
            throw new PropertyCreateException(e);
        }
    }
}
