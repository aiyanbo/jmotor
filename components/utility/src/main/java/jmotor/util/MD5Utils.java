package jmotor.util;

import jmotor.util.exception.MD5EncodeException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Component:Utility
 * Description:MD5 utilities
 * Date: 12-3-23
 *
 * @author Andy.Ai
 */
public class MD5Utils {
    private static final String HEXES = "0123456789abcdef";

    private MD5Utils() {
    }

    public static String encode(String plainText) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(plainText.getBytes("UTF-8"));
            byte[] bytes = messageDigest.digest();
            StringBuilder result = new StringBuilder(bytes.length * 12);
            for (byte b : bytes) {
                result.append(Integer.toHexString((0x000000ff & b) | 0xffffff00).substring(6));
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new MD5EncodeException("Can't get MD5 instance", e);
        } catch (UnsupportedEncodingException e) {
            throw new MD5EncodeException("Unsupported encoding:UTF-8", e);
        }
    }

    public static String getChecksum(File file) {
        InputStream inputStream = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[4096];
            int size;
            while ((size = inputStream.read(bytes)) != -1) {
                digest.update(bytes, 0, size);
            }
            return getHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new MD5EncodeException("Can't get MD5 instance", e);
        } catch (FileNotFoundException e) {
            throw new MD5EncodeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new MD5EncodeException(e.getMessage(), e);
        } finally {
            CloseableUtils.closeQuietly(inputStream);
        }
    }

    public static String getHex(byte[] raw) {
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }
}
