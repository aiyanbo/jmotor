package jmotor.core.ioc.exception;

/**
 * Component:
 * Description:
 * Date: 11-8-23
 *
 * @author Andy.Ai
 */
public class BeanUnDefinedException extends RuntimeException {
    public BeanUnDefinedException() {
    }

    public BeanUnDefinedException(String message) {
        super(message);
    }

    public BeanUnDefinedException(Throwable cause) {
        super(cause);
    }

    public BeanUnDefinedException(String message, Throwable cause) {
        super(message, cause);
    }
}
