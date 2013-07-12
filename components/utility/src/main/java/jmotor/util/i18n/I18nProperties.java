package jmotor.util.i18n;

import jmotor.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Properties;

/**
 * Component:Utility
 * Description:I18n properties
 * Date: 12-2-16
 *
 * @author Andy.Ai
 */
public class I18nProperties extends Properties {
    public static final String SUFFIX = ".properties";

    @Override
    public void load(InputStream inStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inStream, "UTF-8");
        super.load(reader);
    }

    public void load(String name, Locale locale) throws IOException {
        String _locale = locale.getLanguage() + "_" + locale.getCountry();
        load(name, _locale);
    }

    public void load(String name, String locale) throws IOException {
        String fileName = name + "_" + locale + SUFFIX;
        load(StreamUtils.getStream4ClassPath(fileName));
    }
}
