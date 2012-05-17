package jmotor.util;

import java.util.Collection;
import java.util.Map;

/**
 * Component:Utility
 * Description:Assert
 * Date: 11-12-14
 *
 * @author Andy.Ai
 */
public class Assert {
    private Assert() {
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null!");
    }

    public static void isNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNotNull(Object object) {
        isNotNull(object, "[Assertion failed] - the argument is required; it must not be null!");
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - the expression must be true!");
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isFalse(boolean expression) {
        isFalse(expression, "[Assertion failed] - the expression must not be true!");
    }

    public static void isEmpty(Collection collection, String message) {
        if (!collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isEmpty(Collection collection) {
        isEmpty(collection, "[Assertion failed] - the collection must be empty!");
    }

    public static void isNotEmpty(Collection collection, String message) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNotEmpty(Collection collection) {
        isNotEmpty(collection, "[Assertion failed] - the collection must not be empty!");
    }

    public static void isEmpty(Object[] array, String message) {
        if (array.length != 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isEmpty(Object[] array) {
        isEmpty(array, "[Assertion failed] - the array must be empty!");
    }

    public static void isNotEmpty(Object[] array, String message) {
        if (array.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNotEmpty(Object[] array) {
        isNotEmpty(array, "[Assertion failed] - the array must not be empty!");
    }

    public static void isEmpty(Map map, String message) {
        if (!map.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isEmpty(Map map) {
        isEmpty(map, "[Assertion failed] - the map must be empty!");
    }

    public static void isNotEmpty(Map map, String message) {
        if (map.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNotEmpty(Map map) {
        isNotEmpty(map, "[Assertion failed] - the map must not be empty!");
    }

    public static void isBlank(String string, String message) {
        if (!StringUtils.isBlank(string)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isBlank(String string) {
        isBlank(string, "[Assertion failed] - the string must be blank!");
    }

    public static void noNullElements(Object[] array, String message) {
        for (Object element : array) {
            if (element == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    public static void noNullElements(Object[] array) {
        noNullElements(array, "[Assertion failed] - the array must not contain any null elements!");
    }

    public static void noNullElements(Collection collection, String message) {
        for (Object element : collection) {
            if (element == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    public static void noNullElements(Collection collection) {
        noNullElements(collection, "[Assertion failed] - the collection must not contain any null elements!");
    }

    public static void noNullValues(Map map, String message) {
        for (Object key : map.keySet()) {
            if (map.get(key) == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    public static void noNullValues(Map map) {
        noNullValues(map, "[Assertion failed] - the map must not contain any null values!");
    }

    public static void isInstanceOf(Object object, Class clazz, String message) {
        if (!clazz.isInstance(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isInstanceOf(Object object, Class clazz) {
        String message = "[Assertion failed] - "
                + "Object of class [" + (object != null ? object.getClass().getName() : "null") +
                "] must be an instance of " + clazz;
        isInstanceOf(object, clazz, "[]");
    }
}
