package jmotor.core.ioc.util;

import jmotor.core.cache.Cache;
import jmotor.core.ioc.cache.CacheManager;
import jmotor.util.ClassUtils;
import jmotor.util.CollectionUtils;
import jmotor.util.exception.PropertyException;
import jmotor.util.type.MethodType;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Component:IOC
 * Description:Reflection utilities
 * Date: 11-10-19
 *
 * @author Andy.Ai
 */
public class ReflectionUtils {
    private static final Cache classCache = CacheManager.getCache("classCache");
    private static final Cache fieldCache = CacheManager.getCache("fieldCache");
    private static final Cache methodCache = CacheManager.getCache("methodCache");
    private static final Cache propertyDescriptorCache = CacheManager.getCache("propertyDescriptorCache");
    private static final Cache propertyAccessibleCache = CacheManager.getCache("propertyAccessibleCache");

    private ReflectionUtils() {
    }

    public static Object newInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = loadClass(className);
        return clazz.newInstance();
    }

    public static Class<?> loadClass(String className) throws ClassNotFoundException {
        Class clazz = classCache.get(className);
        if (clazz == null) {
            clazz = ClassUtils.loadClass(className);
            classCache.put(className, clazz);
        }
        return clazz;
    }

    public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        String key = clazz.getName() + "." + fieldName;
        Field field = fieldCache.get(key);
        if (field == null) {
            field = ClassUtils.getField(clazz, fieldName);
            fieldCache.put(key, field);
        }
        return field;
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        String key = clazz.getName() + "." + methodName;
        if (CollectionUtils.isNotEmpty(parameterTypes)) {
            for (Class<?> parameterType : parameterTypes) {
                key += parameterType.getCanonicalName() + ",";
            }
        }
        Method method = methodCache.get(key);
        if (method == null) {
            method = ClassUtils.getMethod(clazz, methodName, parameterTypes);
            methodCache.put(key, method);
        }
        return method;
    }

    public static Class<?> getPropertyType(Class<?> clazz, String propertyName) throws NoSuchFieldException {
        Field field = getField(clazz, propertyName);
        return field.getType();
    }

    public static Class<?> getPropertyType(Object object, String propertyName) {
        try {
            return getPropertyType(getClass(object), propertyName);
        } catch (NoSuchFieldException e) {
            throw new PropertyException(e);
        }
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class clazz) throws IntrospectionException {
        String key = clazz.getName();
        PropertyDescriptor[] propertyDescriptors = propertyDescriptorCache.get(key);
        if (propertyDescriptors == null) {
            propertyDescriptors = ClassUtils.getPropertyDescriptors(clazz);
            propertyDescriptorCache.put(key, propertyDescriptors);
        }
        return propertyDescriptors;
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Object object) {
        try {
            return getPropertyDescriptors(getClass(object));
        } catch (IntrospectionException e) {
            throw new PropertyException(e);
        }
    }

    public static PropertyDescriptor getPropertyDescriptor(Class clazz, String propertyName) throws IntrospectionException {
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

    public static Method getAccessible(Class clazz, String propertyName, MethodType methodType) throws IntrospectionException {
        String key = clazz.getName() + "." + propertyName + "." + methodType;
        Method accessible = propertyAccessibleCache.get(key);
        if (accessible == null) {
            PropertyDescriptor propertyDescriptor = getPropertyDescriptor(clazz, propertyName);
            accessible = ClassUtils.getAccessible(propertyDescriptor, methodType);
            propertyAccessibleCache.put(key, accessible);
        }
        return accessible;
    }

    public static Class<?> getClass(Object object) {
        return ClassUtils.getCanonicalClass(object.getClass());
    }

    public static Object invoke(Object object, String methodName, Object... parameters) {
        Class<?>[] parametersType = null;
        if (CollectionUtils.isNotEmpty(parameters)) {
            parametersType = new Class<?>[parameters.length];
            int index = 0;
            for (Object parameter : parameters) {
                parametersType[index++] = getClass(parameter);
            }
        }
        try {
            if (CollectionUtils.isNotEmpty(parametersType)) {
                Method invoker = getMethod(getClass(object), methodName, parametersType);
                return invoker.invoke(object, parameters);
            } else {
                Method invoker = getMethod(getClass(object), methodName);
                return invoker.invoke(object);
            }
        } catch (Exception e) {
            throw new PropertyException(e);
        }
    }

    public static Object getPropertyValue(Object object, String propertyName) {
        Object result;
        try {
            result = getAccessible(getClass(object), propertyName, MethodType.GETTER).invoke(object);
        } catch (Exception e) {
            throw new PropertyException(e);
        }
        return result;
    }

    public static void setPropertyValue(Object object, String propertyName, Object value) {
        try {
            getAccessible(getClass(object), propertyName, MethodType.SETTER).invoke(object, value);
        } catch (Exception e) {
            throw new PropertyException(e);
        }
    }
}
