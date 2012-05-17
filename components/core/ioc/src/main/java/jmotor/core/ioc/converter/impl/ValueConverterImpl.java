package jmotor.core.ioc.converter.impl;

import jmotor.core.ioc.converter.ValueConverter;

import java.math.BigDecimal;

/**
 * Component:
 * Description:
 * Date: 11-8-24
 *
 * @author Andy.Ai
 */
public class ValueConverterImpl implements ValueConverter {
    public Object convert(Class<?> type, String value) {
        String typeName = type.getName();
        if (String.class.getName().equals(typeName)) {
            return value;
        } else if (Character.class.getName().equals(typeName)) {
            return value.trim().substring(0, 1);
        } else if (Integer.class.getName().equals(typeName) || "int".equals(typeName)) {
            return Integer.valueOf(value);
        } else if (Double.class.getName().equals(typeName) || "double".equals(typeName)) {
            return Double.valueOf(value);
        } else if (BigDecimal.class.getName().equals(typeName)) {
            return BigDecimal.valueOf(Double.valueOf(value));
        } else if (Long.class.getName().equals(typeName) || "long".equals(typeName)) {
            return Long.valueOf(value);
        } else if (Short.class.getName().equals(typeName) || "short".equals(typeName)) {
            return Short.valueOf(value);
        } else if (Boolean.class.getName().equals(typeName) || "boolean".equals(typeName)) {
            return Boolean.valueOf(value);
        } else if (Byte.class.getName().equals(typeName) || "byte".equals(typeName)) {
            return Byte.valueOf(value);
        }
        return value;
    }
}
