package jmotor.util;

/**
 * Component:Utility
 * Description:System utilities
 * Date: 12-2-19
 *
 * @author Andy.Ai
 */
public class SystemUtils {
    private SystemUtils() {
    }

    public static String getTemporaryDir() {
        return System.getProperty("java.io.tmpdir");
    }

    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    public static String getPathSeparator() {
        return System.getProperty("path.separator");
    }

    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    public static String getFileEncoding() {
        return System.getProperty("file.encoding");
    }

    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    public static String getUserLanguage() {
        return System.getProperty("user.language");
    }

    public static String getUserName() {
        return System.getProperty("user.name");
    }

    public static String getUserTimezone() {
        return System.getProperty("user.timezone");
    }

    public static String getOSName() {
        return System.getProperty("os.name");
    }

    public static String getOSVersion() {
        return System.getProperty("os.version");
    }

    public static String getJavaHome() {
        return System.getProperty("java.home");
    }
}
