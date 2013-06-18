package jmotor.util.converter;

import java.math.BigDecimal;

/**
 * Component:Utility
 * Description:Simple value converter
 * Date: 11-11-19
 *
 * @author Andy.Ai
 */
public class SimpleValueConverter {
    private SimpleValueConverter() {
    }

    public static Object convert(Class<?> type, String value) {
        String typeName = type.getName();
        if (String.class.getName().equals(typeName)) {
            return value;
        } else if (Character.class.getName().equals(typeName)) {
            return value.trim().substring(0, 1);
        } else if (Integer.class.getName().equals(typeName) || "int".equals(typeName)) {
            return Integer.valueOf(value);
        } else if (Double.class.getName().equals(typeName) || "double".equals(typeName)) {
            return Double.valueOf(value);
        } else if (Float.class.getName().equals(typeName) || "float".equals(typeName)) {
            return Float.valueOf(value);
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
