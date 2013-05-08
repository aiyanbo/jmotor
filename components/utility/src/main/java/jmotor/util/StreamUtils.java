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
import java.io.OutputStream;
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
    public static final int EOF = -1;
    public static final int DEFAULT_BUFFER_SIZE = 1024 * 10;

    private StreamUtils() {
    }

    public static byte[] transform(InputStream inputStream) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            int data;
            while ((data = inputStream.read()) != EOF) {
                os.write(data);
            }
            return os.toByteArray();
        } finally {
            CloseableUtils.closeQuietly(inputStream);
            CloseableUtils.closeQuietly(os);
        }
    }

    public static String transform(InputStreamReader inputStreamReader) throws IOException {
        StringBuilder result = new StringBuilder();
        char[] chars = new char[DEFAULT_BUFFER_SIZE];
        int size;
        try {
            while ((size = inputStreamReader.read(chars)) > 0) {
                result.append(chars, 0, size);
            }
        } finally {
            CloseableUtils.closeQuietly(inputStreamReader);
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
            CloseableUtils.closeQuietly(objectOutputStream);
            CloseableUtils.closeQuietly(fileOutputStream);
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
            CloseableUtils.closeQuietly(objectInputStream);
            CloseableUtils.closeQuietly(fileInputStream);
        }
        return (T) result;
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copyLarge(inputStream, outputStream, new byte[DEFAULT_BUFFER_SIZE]);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count = 0;
        int size = 0;
        while (EOF != (size = input.read(buffer))) {
            output.write(buffer, 0, size);
            count += size;
        }
        return count;
    }
}
