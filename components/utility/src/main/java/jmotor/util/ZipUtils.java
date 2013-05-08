package jmotor.util;

import jmotor.util.exception.UnzipException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Component:
 * Description:
 * Date: 13-5-8
 *
 * @author Andy Ai
 */
public class ZipUtils {
    private ZipUtils() {
    }

    public static List<File> unzip(String zipFileName, String directoryName) {
        return unzip(new File(zipFileName), directoryName);
    }

    public static List<File> unzip(File file, String directoryName) {
        return unzip(file, new File(directoryName));
    }

    @SuppressWarnings("unchecked")
    public static List<File> unzip(File file, File directory) {
        List<File> files = new ArrayList<File>();
        ZipFile zipFile = null;
        try {
            FileUtils.forceMkdir(directory);
            zipFile = new ZipFile(file);
            Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                String fileName = directory.getAbsolutePath() + File.separator + zipEntry.getName();
                OutputStream outputStream = new FileOutputStream(fileName);
                InputStream inputStream = zipFile.getInputStream(zipEntry);
                byte[] data = new byte[10485760];
                int size;
                while ((size = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, size);
                }
                outputStream.close();
                inputStream.close();
                files.add(new File(fileName));
            }
        } catch (Exception e) {
            throw new UnzipException(e.getMessage(), e);
        } finally {
            // ZipFile is not implementation Closeable in jdk6
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return files;
    }

    public static File ungzip(File gzip, File directory) {
        GZIPInputStream gzipInputStream = null;
        FileOutputStream outputStream = null;
        File file = new File(directory, UUID.randomUUID().toString());
        try {
            FileInputStream fin = new FileInputStream(gzip);
            gzipInputStream = new GZIPInputStream(fin);
            outputStream = new FileOutputStream(file);
            byte[] data = new byte[8096];
            int size;
            while ((size = gzipInputStream.read(data)) != -1) {
                outputStream.write(data, 0, size);
            }
            return file;
        } catch (IOException e) {
            throw new UnzipException(e.getMessage(), e);
        } finally {
            CloseableUtils.closeQuietly(gzipInputStream);
            CloseableUtils.closeQuietly(outputStream);
        }
    }
}
