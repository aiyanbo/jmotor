package jmotor.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Component:
 * Description:
 * Date: 13-5-8
 *
 * @author Andy Ai
 */
public class CloseableUtils {
    private CloseableUtils() {
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                //ignore the exception
            }
        }
    }

    public static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                //ignore the exception
            }
        }
    }
}
