package jmotor.plugin.hibernate.transformer;

import jmotor.plugin.hibernate.exception.ResultTransformException;
import jmotor.util.ClassUtils;
import jmotor.util.StringUtils;
import jmotor.util.converter.SimpleValueConverter;
import jmotor.util.type.MethodType;
import org.hibernate.transform.ResultTransformer;

import javax.persistence.Column;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Component:Hibernate-plugin
 * Description:Simple result transformer
 * Date: 12-3-20
 *
 * @author Andy.Ai
 */
public class SimpleResultTransformer implements ResultTransformer {
    private Class<?> mappingClass;

    @Override
    public Object transformTuple(Object[] objects, String[] strings) {
        try {
            Object entity = mappingClass.newInstance();
            PropertyDescriptor[] propertyDescriptors = ClassUtils.getPropertyDescriptors(mappingClass);
            for (int i = 0; i < strings.length; i++) {
                if (objects[i] == null) {
                    continue;
                }
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    Method getter = propertyDescriptor.getReadMethod();
                    Column column = getter.getAnnotation(Column.class);
                    if (column == null) {
                        Field field = ClassUtils.getField(mappingClass, StringUtils.nameOfProperty(strings[i]));
                        if (field != null) {
                            column = field.getAnnotation(Column.class);
                        }
                    }
                    Method setter = null;
                    if (column != null) {
                        if (strings[i].equals(column.name())) {
                            setter = propertyDescriptor.getWriteMethod();
                        }
                    } else {
                        setter = ClassUtils.getAccessible(mappingClass, StringUtils.nameOfProperty(strings[i]), MethodType.SETTER);
                    }
                    if (setter != null) {
                        Object value = SimpleValueConverter.convert(setter.getParameterTypes()[0], String.valueOf(objects[i]));
                        setter.invoke(entity, value);
                    }
                }
            }
            return entity;
        } catch (Exception e) {
            throw new ResultTransformException(e);
        }
    }

    @Override
    public List transformList(List list) {
        return list;
    }

    public void setMappingClass(Class<?> mappingClass) {
        this.mappingClass = mappingClass;
    }
}
