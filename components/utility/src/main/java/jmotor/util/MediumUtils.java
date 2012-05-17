package jmotor.util;

import java.io.IOException;

/**
 * Component:Utility
 * Description:Medium utilities
 * Date: 12-2-29
 *
 * @author Andy.Ai
 */
public class MediumUtils {
    private MediumUtils() {
    }

    public static String convert2Offline(String htmlContent) {
        return null;
    }

    public static String getImageSource(String path) throws IOException {
        return getSource(path, "image");
    }

    public static String getSource(String path, String type) throws IOException {
        StringBuilder result = new StringBuilder("data:");
        result.append(type).append('/').append(FileUtils.getFileSuffix(path)).append(',');
        result.append(Base64.encodeBytes(FileUtils.readFile(path)));
        return result.toString();
    }
}
