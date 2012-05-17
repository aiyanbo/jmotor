package jmotor.core.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Properties;

/**
 * Component:Core
 * Description:I18n manager
 * Date: 12-3-10
 *
 * @author Andy.Ai
 */
public interface I18nManager {
    String getI18nValue(String key);

    String getI18nValue(String key, Locale locale);

    Properties getResources(String[] baseNames, Locale locale);

    Properties getResources(String baseNames, Locale locale);

    void register(String baseName);

    Properties register(String path, Locale locale) throws IOException;

    void register4FileSystem(String baseName);

    Properties register4FileSystem(String path, Locale locale) throws IOException;

    Properties register(InputStream inputStream, Locale locale) throws IOException;

    Properties register(byte[] content, Locale locale) throws UnsupportedEncodingException;

}
