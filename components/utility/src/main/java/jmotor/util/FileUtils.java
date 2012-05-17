package jmotor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

/**
 * Component:Utility
 * Description:File utilities
 * Date: 11-8-18
 *
 * @author Andy.Ai
 */
public class FileUtils {
    private static final SecurityManager securityManager = new SecurityManager() {
        public void checkPermission(Permission perm) {
        }
    };
    private static final SecurityManager defSecurityManager = System.getSecurityManager();

    private FileUtils() {
    }

    public static InputStream loadFileStream(String path) throws FileNotFoundException {
        return loadFileStream(new File(path));
    }

    public static InputStream loadFileStream(File file) throws FileNotFoundException {
        if (file.exists() && !file.isDirectory()) {
            return new FileInputStream(file);
        }
        throw new FileNotFoundException(file.getPath() + ":Can't found or it's directory");
    }

    public static List<String> scanAllFiles(String path) {
        return scanFiles(path, false, null);
    }

    public static List<String> scanFiles(String path) {
        return scanFiles(path, true, null);
    }

    public static List<String> scanFiles(String path, FilenameFilter filenameFilter) {
        return scanFiles(path, true, filenameFilter);
    }

    public static List<String> scanFiles(String path, boolean excludeHidden, FilenameFilter filenameFilter) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        List<String> absolutePaths = new ArrayList<String>();
        File[] files = file.listFiles(filenameFilter);
        if (CollectionUtils.isEmpty(files)) {
            return null;
        }
        for (File _file : files) {
            if (excludeHidden && _file.isHidden()) {
                continue;
            }
            if (_file.isDirectory()) {
                List<String> subFiles = scanFiles(_file.getAbsolutePath(), excludeHidden, filenameFilter);
                if (subFiles != null) {
                    absolutePaths.addAll(subFiles);
                }
            } else {
                absolutePaths.add(_file.getAbsolutePath());
            }
        }
        return absolutePaths.size() > 0 ? absolutePaths : null;
    }

    public static byte[] readFile(String path) throws IOException {
        InputStream inputStream = loadFileStream(path);
        try {
            return StreamUtils.transform(inputStream);
        } finally {
            inputStream.close();
        }
    }

    public static void writeFile(String path, byte[] content) throws IOException {
        writeFile(path, content, false);
    }

    public static boolean ensurePath(String path) {
        File file = new File(path);
        return ensureFile(file);
    }

    public static boolean ensureFile(File file) {
        File parentDir = file.getParentFile();
        boolean writable = false;
        if (!parentDir.exists()) {
            writable = parentDir.mkdirs();
        }
        return writable;
    }

    public static void writeFile(String path, byte[] content, boolean append) throws IOException {
        File file = new File(path);
        if (ensureFile(file)) {
            FileOutputStream fileOutputStream = new FileOutputStream(file, append);
            try {
                fileOutputStream.write(content);
                fileOutputStream.flush();
            } finally {
                fileOutputStream.close();
            }
        }
    }

    public static void writeFile(String path, InputStream inputStream, boolean append) throws IOException {
        File file = new File(path);
        if (ensureFile(file)) {
            OutputStream outputStream = new FileOutputStream(file, append);
            try {
                byte[] bytes = new byte[1024];
                int size;
                while ((size = inputStream.read(bytes)) > 0) {
                    outputStream.write(bytes, 0, size);
                }
                outputStream.flush();
            } finally {
                inputStream.close();
                outputStream.close();
            }
        }
    }

    public static void writeFile(String path, InputStream inputStream, boolean append, String encoding) throws IOException {
        File file = new File(path);
        if (ensureFile(file)) {
            OutputStream outputStream = new FileOutputStream(file, append);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, encoding);
            try {
                byte[] bytes = new byte[1024];
                int size;
                while ((size = inputStream.read(bytes)) > 0) {
                    outputStream.write(bytes, 0, size);
                }
                outputStream.flush();
            } finally {
                inputStream.close();
                outputStream.close();
                writer.close();
            }
        }
    }

    public static void writeUTF8File(String path, InputStream inputStream, boolean append) throws IOException {
        writeFile(path, inputStream, append, "utf-8");
    }

    public static void writeUTF8File(String path, InputStream inputStream) throws IOException {
        writeUTF8File(path, inputStream, false);
    }

    public static boolean deleteFile(String path) {
        return deleteFile(new File(path));
    }

    public static boolean deleteFile(File file) {
        return deleteFile(file, null);
    }

    public static boolean deleteFile(File file, FilenameFilter filenameFilter) {
        System.setSecurityManager(securityManager);
        if (file.isDirectory()) {
            File[] listFiles;
            if (filenameFilter == null) {
                listFiles = file.listFiles();
            } else {
                listFiles = file.listFiles(filenameFilter);
            }
            for (File fileChild : listFiles) {
                deleteFile(fileChild, filenameFilter);
            }
        }
        boolean successful = file.delete();
        System.setSecurityManager(defSecurityManager);
        return successful;
    }

    public static String getFileSuffix(String path) {
        File file = new File(path);
        if (file.exists()) {
            int dotIndex = path.lastIndexOf('.');
            if (dotIndex > -1) {
                return path.substring(++dotIndex);
            }
        }
        return null;
    }
}
