package jmotor.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.UUID;

/**
 * Component:Utility
 * Description:Stream utilities
 * Date: 11-8-16
 *
 * @author Andy.Ai
 */
public class StreamUtils {
    private StreamUtils() {
    }

    public static byte[] transform(InputStream inputStream) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            int data;
            while ((data = inputStream.read()) != -1) {
                os.write(data);
            }
            return os.toByteArray();
        } finally {
            inputStream.close();
            os.close();
        }
    }

    public static String transform(InputStreamReader inputStreamReader) throws IOException {
        StringBuilder result = new StringBuilder();
        char[] chars = new char[1024];
        int size;
        try {
            while ((size = inputStreamReader.read(chars)) > 0) {
                result.append(chars, 0, size);
            }
        } finally {
            inputStreamReader.close();
        }
        return result.toString();
    }

    public static InputStream transform(String str, String charset) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(str.getBytes(charset));
    }

    public static InputStream transform(String str) {
        return new ByteArrayInputStream(str.getBytes());
    }

    public static InputStream getStream4ClassPath(String name) {
        return ClassUtils.getClassLoader().getResourceAsStream(name);
    }

    public static InputStream getStream4Url(String url) throws IOException {
        return new URL(url).openStream();
    }

    public static InputStream getStream4FileSystem(String path) throws FileNotFoundException {
        return FileUtils.loadFileStream(path);
    }

    public static String writeObject(Serializable serializable) {
        String fileName = SystemUtils.getTemporaryDir() + "jmotor" + SystemUtils.getFileSeparator()
                + UUID.randomUUID().toString() + ".out";
        try {
            writeObject(serializable, fileName);
        } catch (IOException e) {
            return null;
        }
        return fileName;
    }

    public static void writeObject(Serializable serializable, String fileName) throws IOException {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            FileUtils.ensurePath(fileName);
            fileOutputStream = new FileOutputStream(fileName);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(serializable);
        } finally {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readObject(String fileName) throws IOException, ClassNotFoundException {
        Object result = null;
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            result = objectInputStream.readObject();
        } finally {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
        return (T) result;
    }
}
