package jmotor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Component:Utility
 * Description:Collection utilities
 * Date: 11-8-16
 *
 * @author Andy.Ai
 */
public class CollectionUtils {
    private CollectionUtils() {
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public static Object[] removeElement(Object[] array, Object element) {
        Object[] newArray = new Object[array.length - 1];
        int index = 0;
        for (Object entry : array) {
            if (!entry.equals(element)) {
                newArray[index++] = entry;
            }
        }
        return newArray;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean contains(Collection<?> collection, String propertyName, Object value) {
        return find(collection, propertyName, value) != null;
    }

    public static boolean contains(Object[] array, Object object) {
        for (Object entry : array) {
            if (entry.equals(object)) {
                return true;
            }
        }
        return false;
    }

    public static Object find(Object[] array, Validator validator) {
        for (Object entry : array) {
            if (validator.validate(entry)) {
                return entry;
            }
        }
        return null;
    }

    public static void forAllExecute(Collection collection, Executor executor) {
        for (Object entry : collection) {
            executor.execute(entry);
        }
    }

    public static <T> T find(Collection<T> collection, String propertyName, Object value) {
        for (T object : collection) {
            Object existsValue = ObjectUtils.getPropertyValue(object, propertyName);
            if (existsValue.equals(value)) {
                return object;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T find(Collection collection, Validator validator) {
        for (Object entry : collection) {
            if (validator.validate(entry)) {
                return (T) entry;
            }
        }
        return null;
    }

    public static void filter(Collection collection, Validator validator) {
        for (Iterator iterator = collection.iterator(); iterator.hasNext(); ) {
            if (!validator.validate(iterator.next())) {
                iterator.remove();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static List select(Collection collection, Validator validator) {
        List result = new ArrayList(collection.size());
        select(collection, result, validator);
        return result;
    }

    @SuppressWarnings("unchecked")
    public static void select(Collection data, Collection container, Validator validator) {
        for (Object entry : data) {
            if (validator.validate(entry)) {
                container.add(entry);
            }
        }
    }
}                                                             
