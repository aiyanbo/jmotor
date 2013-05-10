package jmotor.util;

import jmotor.util.type.MethodType;

import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Component:Utility
 * Description:Class utilities
 * Date: 11-8-16
 *
 * @author Andy.Ai
 */
public class ClassUtils {
    private static final Set<Class<?>> PRIMITIVE_CLASSES = new HashSet<Class<?>>();

    static {
        PRIMITIVE_CLASSES.add(String.class);
        PRIMITIVE_CLASSES.add(Boolean.class);
        PRIMITIVE_CLASSES.add(Byte.class);
        PRIMITIVE_CLASSES.add(Character.class);
        PRIMITIVE_CLASSES.add(Short.class);
        PRIMITIVE_CLASSES.add(Integer.class);
        PRIMITIVE_CLASSES.add(Long.class);
        PRIMITIVE_CLASSES.add(Double.class);
        PRIMITIVE_CLASSES.add(Float.class);
        PRIMITIVE_CLASSES.add(Void.TYPE);
    }

    private ClassUtils() {
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Object newInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = loadClass(className);
        return clazz.newInstance();
    }

    public static Class<?> loadClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> _clazz = clazz;
        while (true) {
            try {
                return _clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                _clazz = _clazz.getSuperclass();
                if (_clazz == null) {
                    throw e;
                }
            }
        }
    }

    public static Field[] getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        Class<?> _clazz = clazz;
        while (true) {
            Field[] classFields = _clazz.getDeclaredFields();
            Collections.addAll(fields, classFields);
            if (_clazz.getSuperclass().equals(Object.class)) {
                break;
            } else {
                _clazz = _clazz.getSuperclass();
            }
        }
        return fields.toArray(new Field[fields.size()]);
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Class<?> _clazz = clazz;
        while (true) {
            try {
                return _clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                _clazz = _clazz.getSuperclass();
                if (_clazz == null) {
                    throw e;
                }
            }
        }
    }

    public static Method[] getMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<Method>();
        Class<?> _clazz = clazz;
        while (true) {
            Method[] classMethods = _clazz.getDeclaredMethods();
            Collections.addAll(methods, classMethods);
            if (_clazz.getSuperclass().equals(Object.class)) {
                break;
            } else {
                _clazz = _clazz.getSuperclass();
            }
        }
        return methods.toArray(new Method[methods.size()]);
    }

    public static Class<?> getPropertyType(Class<?> clazz, String propertyName) throws NoSuchFieldException {
        Field field = getField(clazz, propertyName);
        return field.getType();
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) throws IntrospectionException {
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        List<PropertyDescriptor> result = new ArrayList<PropertyDescriptor>(propertyDescriptors.length);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if ("class".equals(propertyDescriptor.getName()) || propertyDescriptor instanceof IndexedPropertyDescriptor) {
                continue;
            }
            result.add(propertyDescriptor);
        }
        return result.toArray(new PropertyDescriptor[result.size()]);
    }

    public static PropertyDescriptor[] getDeclaredPropertyDescriptors(Class<?> clazz) throws IntrospectionException {
        List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>();
        PropertyDescriptor[] _propertyDescriptors = getPropertyDescriptors(clazz);
        Field[] fields = getFields(clazz);
        for (PropertyDescriptor propertyDescriptor : _propertyDescriptors) {
            for (Field field : fields) {
                if (propertyDescriptor.getName().equals(field.getName())) {
                    propertyDescriptors.add(propertyDescriptor);
                }
            }
        }
        return propertyDescriptors.toArray(new PropertyDescriptor[propertyDescriptors.size()]);
    }

    public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName) throws IntrospectionException {
        PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(clazz);
        if (CollectionUtils.isNotEmpty(propertyDescriptors)) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                if (propertyDescriptor.getName().equals(propertyName)) {
                    return propertyDescriptor;
                }
            }
        }
        throw new IntrospectionException("Can't found:" + clazz.getName() + "." + propertyName);
    }

    public static Method getAccessible(Class<?> clazz, String propertyName, MethodType methodType) throws IntrospectionException {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(clazz, propertyName);
        return getAccessible(propertyDescriptor, methodType);
    }

    public static Method getAccessible(PropertyDescriptor propertyDescriptor, MethodType methodType) {
        Method accessible = null;
        switch (methodType) {
            case SETTER:
                accessible = propertyDescriptor.getWriteMethod();
                break;
            case GETTER:
                accessible = propertyDescriptor.getReadMethod();
                break;
            default:
                break;
        }
        return accessible;
    }

    public static Class<?>[] getSuperClasses(Class clazz) {
        List<Class<?>> supperClasses = new ArrayList<Class<?>>();
        Class<?> _clazz = clazz;
        while (true) {
            Class<?> supperClass = _clazz.getSuperclass();
            if (supperClass != null) {
                supperClasses.add(supperClass);
                _clazz = supperClass;
            } else {
                break;
            }
        }
        return supperClasses.toArray(new Class<?>[supperClasses.size()]);
    }

    public static boolean isRelationship(Class clazz, Class superClass) {
        Class<?>[] superClasses = getSuperClasses(clazz);
        for (Class<?> _superClass : superClasses) {
            if (_superClass.equals(superClass)) {
                return true;
            }
        }
        Class<?>[] interfaces = getInterfaces(clazz);
        if (CollectionUtils.isNotEmpty(interfaces)) {
            for (Class<?> _interface : interfaces) {
                if (_interface.equals(superClass)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Class<?>[] getInterfaces(Class clazz) {
        List<Class<?>[]> interfaces = new ArrayList<Class<?>[]>();
        Class<?>[] _interfaces = clazz.getInterfaces();
        if (_interfaces != null) {
            interfaces.add(_interfaces);
            for (Class<?> _interface : _interfaces) {
                Class<?>[] subClassInterfaces = getInterfaces(_interface);
                if (subClassInterfaces != null) {
                    interfaces.add(subClassInterfaces);
                }
            }
        }
        List<Class<?>> result = new ArrayList<Class<?>>();
        for (Class<?>[] classes : interfaces) {
            if (classes != null) {
                Collections.addAll(result, classes);
            }
        }
        if (CollectionUtils.isNotEmpty(result)) {
            return result.toArray(new Class<?>[result.size()]);
        }
        return null;
    }

    public static Annotation[] getAnnotations(PropertyDescriptor propertyDescriptor, MethodType methodType) {
        Method method = getAccessible(propertyDescriptor, methodType);
        if (method != null) {
            return method.getAnnotations();
        }
        return null;
    }

    public static Class getCanonicalClass(Class<?> clazz) {
        Class _clazz = clazz;
        if (clazz.getName().indexOf('$') != -1) {
            _clazz = clazz.getSuperclass();
        }
        return _clazz;
    }

    public static boolean isPrimitiveClass(Class<?> clazz) {
        return PRIMITIVE_CLASSES.contains(clazz);
    }
}
