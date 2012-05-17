package jmotor.core.i18n;

import jmotor.util.StringUtils;
import jmotor.util.SystemUtils;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Component:
 * Description:
 * Date: 12-3-11
 *
 * @author Andy.Ai
 */
public class I18nResourceProperties extends Properties {
    @Override
    public void load(InputStream inStream) throws IOException {
        UniversalDetector detector = new UniversalDetector(null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String encoding;
        byte[] content;
        try {
            byte[] bytes = new byte[1024 * 4];
            int size;
            while ((size = inStream.read(bytes)) > 0 && !detector.isDone()) {
                detector.handleData(bytes, 0, size);
                byteArrayOutputStream.write(bytes, 0, size);
            }
            detector.dataEnd();
            content = byteArrayOutputStream.toByteArray();
            encoding = detector.getDetectedCharset();
        } finally {
            inStream.close();
            byteArrayOutputStream.close();
        }
        encoding = StringUtils.isBlank(encoding) ? "ASCII" : encoding;
        load(content, encoding);
    }

    public void load(byte[] content, String encoding) throws UnsupportedEncodingException {
        String resource = new String(content, 0, content.length, encoding);
        String[] mapping = StringUtils.split(resource, SystemUtils.getLineSeparator());
        for (String entry : mapping) {
            String[] keyValue = StringUtils.split(entry, "=");
            if (keyValue.length == 1) {
                put(keyValue[0], "");
            } else {
                put(keyValue[0], keyValue[1]);
            }
        }
    }
}
