package jmotor.util.exception;

/**
 * Component:
 * Description:
 * Date: 12-3-23
 *
 * @author Andy.Ai
 */
public class MD5EncodeException extends RuntimeException {
    public MD5EncodeException() {
        super();
    }

    public MD5EncodeException(String message) {
        super(message);
    }

    public MD5EncodeException(Throwable cause) {
        super(cause);
    }

    public MD5EncodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
