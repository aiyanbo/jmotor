package jmotor.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Component:Utility
 * Description:String utilities
 * Date: 11-8-10
 *
 * @author Andy.Ai
 */
public class StringUtils {
    public static final String EMPTY = "";
    public static final String AND_MARK = "&";
    public static final String COMMA = ",";
    public static final String SEMICOLON = ";";
    public static final String QUESTION_MARK = "?";

    private StringUtils() {
    }

    public static boolean isBlank(String str) {
        return isNull(str) || str.trim().length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isNull(String str) {
        return null == str;
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    public static String replaceNull(String str) {
        return replaceNull(str, EMPTY);
    }

    public static String replaceNull(String str, String replacement) {
        if (str == null) {
            return replacement;
        }
        return str;
    }

    public static String deleteWhitespace(String str) {
        int length = str.length();
        char[] resultChars = new char[length];
        int count = 0;
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                resultChars[count++] = str.charAt(i);
            }
        }
        if (count == length) {
            return str;
        }
        return new String(resultChars, 0, count);
    }

    public static String remove(String str, String removeText) {
        return replace(str, removeText, EMPTY, -1);
    }

    public static String replace(String str, String destination, String replacement) {
//        int startIndex = 0;
//        int endIndex = str.indexOf(destination);
//        StringBuilder result = new StringBuilder("");
//        if (endIndex != -1) {
//            while (endIndex != -1) {
//                result.append(str.substring(startIndex, endIndex));
//                result.append(replacement);
//                startIndex = endIndex + destination.length();
//                endIndex = str.indexOf(destination, startIndex);
//            }
//            result.append(str.substring(startIndex));
//        } else {
//            return str;
//        }
//        return result.toString();
        return replace(str, destination, replacement, -1);
    }

    public static String replaceOnce(String str, String destination, String replacement) {
        return replace(str, destination, replacement, 1);
    }

    public static String replace(String str, String destination, String replacement, int max) {
        int startIndex = 0;
        int endIndex = str.indexOf(destination, startIndex);
        if (endIndex == -1) {
            return str;
        }
        int destinationLength = destination.length();
        int increase = replacement.length() - destinationLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StringBuilder resultBuilder = new StringBuilder(str.length() + increase);
        while (endIndex != -1) {
            resultBuilder.append(str.substring(startIndex, endIndex)).append(replacement);
            startIndex = endIndex + destinationLength;
            if (--max == 0) {
                break;
            }
            endIndex = str.indexOf(destination, startIndex);
        }
        resultBuilder.append(str.substring(startIndex));
        return resultBuilder.toString();
    }

    public static String[] split(String str, String separator) {
        StringTokenizer tokenizer = new StringTokenizer(str, separator);
        String[] result = new String[tokenizer.countTokens()];
        int index = 0;
        while (tokenizer.hasMoreTokens()) {
            result[index++] = tokenizer.nextToken();
        }
        return result;
    }

    public static int countMatches(String str, String sub) {
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(sub, index)) != -1) {
            count++;
            index += sub.length();
        }
        return count;
    }

    public static String join(Object[] objects, String separator) {
        StringBuilder result = new StringBuilder(1024);
        for (int i = 0; i < objects.length; i++) {
            String str = ObjectUtils.toString(objects[i]);
            if (isBlank(str)) {
                continue;
            }
            result.append(str);
            if (i < objects.length - 1) {
                result.append(separator);
            }
        }
        String text = result.toString();
        return text.endsWith(separator) ? text.substring(0, text.length() - separator.length()) : text;
    }

    public static String join(Collection collection, String separator) {
        return join(collection.iterator(), separator);
    }

    public static String join(Iterator iterator, String separator) {
        StringBuilder result = new StringBuilder(1024);
        while (iterator.hasNext()) {
            String str = ObjectUtils.toString(iterator.next());
            if (isBlank(str)) {
                continue;
            }
            result.append(str);
            result.append(separator);
        }
        String text = result.toString();
        return text.endsWith(separator) ? text.substring(0, text.length() - separator.length()) : text;
    }

    public static String join(String str, int repetitions, String separator) {
        int increase = str.length() * repetitions + repetitions * separator.length();
        StringBuilder result = new StringBuilder(increase);
        result.append(str);
        while (repetitions-- > 1) {
            result.append(separator);
            result.append(str);
        }
        return result.toString();
    }

    public static String surround(String originalText, String surroundText) {
        return surroundText + originalText + surroundText;
    }

    public static String insertBefore(String str, String destination, String insertText) {
        int index = str.indexOf(destination);
        return insert(str, index, insertText);
    }

    public static String insertAfter(String str, String destination, String insertText) {
        int index = str.indexOf(destination);
        return insert(str, ++index, insertText);
    }

    public static String insert(String str, int index, String insertText) {
        StringBuilder result = new StringBuilder(str);
        result.insert(index, insertText);
        return result.toString();
    }

    public static String nameOfDatabase(String fieldName) {
        StringBuilder result = new StringBuilder(fieldName.length() * 2);
        char[] chars = fieldName.toCharArray();
        for (char _char : chars) {
            if (Character.isUpperCase(_char)) {
                result.append('_');
                result.append(Character.toLowerCase(_char));
            } else {
                result.append(_char);
            }
        }
        return result.toString();
    }

    public static String nameOfProperty(String fieldName) {
        if (fieldName.indexOf('_') == -1) {
            return fieldName;
        }
        StringBuilder result = new StringBuilder(fieldName.length());
        boolean upperCase = false;
        int lastIndex = fieldName.lastIndexOf('_');
        for (int i = 0; i < fieldName.length(); i++) {
            if (i > lastIndex + 1) {
                result.append(fieldName.substring(lastIndex + 2));
                break;
            }
            char _char = fieldName.charAt(i);
            if ('_' == _char) {
                upperCase = true;
                continue;
            }
            if (upperCase) {
                result.append(upperCase(_char));
                upperCase = false;
            } else {
                result.append(_char);
            }
        }
        return result.toString();
    }

    public static String upperCase(char _char) {
        return String.valueOf(Character.toUpperCase(_char));
    }

    public static String lowerCase(char _char) {
        return String.valueOf(Character.toLowerCase(_char));
    }

    public static boolean isUpperCase(String str) {
        return str.equals(str.toUpperCase());
    }

    public static boolean isLowerCase(String str) {
        return str.equals(str.toLowerCase());
    }

    public static String firstCharacter(String str) {
        return String.valueOf(str.charAt(0));
    }

    public static String lastCharacter(String str) {
        return String.valueOf(str.charAt(str.length() - 1));
    }

    public static boolean contains(String str, String separator) {
        return !isBlank(str) && str.contains(separator);
    }

    public static String trim(String value) {
        return value == null ? null : value.trim();
    }
}
