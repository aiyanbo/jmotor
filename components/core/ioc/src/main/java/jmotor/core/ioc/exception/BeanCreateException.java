package jmotor.core.ioc.exception;

/**
 * Component:
 * Description:
 * Date: 11-8-24
 *
 * @author Andy.Ai
 */
public class BeanCreateException extends RuntimeException {
    public BeanCreateException() {
    }

    public BeanCreateException(String message) {
        super(message);
    }

    public BeanCreateException(Throwable cause) {
        super(cause);
    }

    public BeanCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
