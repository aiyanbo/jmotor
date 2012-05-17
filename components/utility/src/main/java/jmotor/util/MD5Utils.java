package jmotor.util;

import jmotor.util.exception.MD5EncodeException;

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
}
