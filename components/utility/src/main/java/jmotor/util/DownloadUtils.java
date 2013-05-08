package jmotor.util;

import jmotor.util.exception.DownloadException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Component:
 * Description:
 * Date: 13-5-8
 *
 * @author Andy Ai
 */
public class DownloadUtils {
    private DownloadUtils() {
    }

    public static File download(String directory, String downloadPath) {
        File dir = new File(directory);
        try {
            FileUtils.forceMkdir(dir);
        } catch (IOException e) {
            throw new DownloadException("can't create dir: " + directory, e);
        }
        return download(dir, downloadPath);
    }

    public static File download(File directory, String downloadPath) {
        String fileName = downloadPath.substring(downloadPath.lastIndexOf('/') + 1);
        File file = new File(directory, fileName);
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(downloadPath);
            outputStream = new FileOutputStream(file);
            inputStream = url.openStream();
            StreamUtils.copyLarge(inputStream, outputStream);
        } catch (MalformedURLException e) {
            throw new DownloadException("can't find the url: " + downloadPath, e);
        } catch (IOException e) {
            throw new DownloadException("can't open stream" + downloadPath, e);
        } finally {
            CloseableUtils.closeQuietly(inputStream);
            CloseableUtils.closeQuietly(outputStream);
        }
        return file;
    }

    public static File download(String directory, String fileName, String downloadPath, String checksum) {
        File dir = new File(directory);
        try {
            FileUtils.forceMkdir(dir);
        } catch (IOException e) {
            throw new DownloadException("can't create dir: " + directory, e);
        }
        return download(new File(directory), fileName, downloadPath, checksum);
    }

    public static File download(File directory, String fileName, String downloadPath, String checksum) {
        String _fileName = fileName;
        if (StringUtils.isBlank(fileName)) {
            _fileName = UUID.randomUUID().toString();
        }
        File file = new File(directory, _fileName);
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            URL url = new URL(downloadPath);
            outputStream = new FileOutputStream(file);
            inputStream = url.openStream();
            byte[] bytes = new byte[1048576];
            int size;
            while ((size = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, size);
                digest.update(bytes, 0, size);
            }
            String _checksum = MD5Utils.getHex(digest.digest());
            if (ObjectUtils.isDifferent(checksum, _checksum)) {
                throw new DownloadException("checksum is not same, checksum: " + _checksum);
            }
        } catch (MalformedURLException e) {
            throw new DownloadException("can't find the url: " + downloadPath, e);
        } catch (IOException e) {
            throw new DownloadException("can't open stream" + downloadPath, e);
        } catch (NoSuchAlgorithmException e) {
            throw new DownloadException("can't get md5 instance" + downloadPath, e);
        } finally {
            CloseableUtils.closeQuietly(inputStream);
            CloseableUtils.closeQuietly(outputStream);
        }
        return file;
    }
}
