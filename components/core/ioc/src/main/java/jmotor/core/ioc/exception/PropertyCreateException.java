package jmotor.core.ioc.exception;

/**
 * Component:
 * Description:
 * Date: 11-11-19
 *
 * @author Andy.Ai
 */
public class PropertyCreateException extends RuntimeException {
    public PropertyCreateException() {
        super();
    }

    public PropertyCreateException(String message) {
        super(message);
    }

    public PropertyCreateException(Throwable cause) {
        super(cause);
    }

    public PropertyCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
