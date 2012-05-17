package jmotor.util.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Component:Utility
 * Description:I18n properties
 * Date: 12-2-16
 *
 * @author Andy.Ai
 */
public class I18nProperties extends Properties {
    @Override
    public void load(InputStream inStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inStream, "UTF-8");
        super.load(reader);
    }
}
