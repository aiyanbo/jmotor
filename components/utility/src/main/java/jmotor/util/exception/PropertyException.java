package jmotor.util.exception;

/**
 * Component:
 * Description:
 * Date: 11-8-16
 *
 * @author Andy.Ai
 */
public class PropertyException extends RuntimeException {
    public PropertyException() {
        super();
    }

    public PropertyException(String message) {
        super(message);
    }

    public PropertyException(Throwable cause) {
        super(cause);
    }

    public PropertyException(String message, Throwable cause) {
        super(message, cause);
    }
}
