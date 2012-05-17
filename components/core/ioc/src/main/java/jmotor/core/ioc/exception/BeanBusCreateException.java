package jmotor.core.ioc.exception;

/**
 * Component:
 * Description:
 * Date: 11-8-17
 *
 * @author Andy.Ai
 */
public class BeanBusCreateException extends RuntimeException {
    public BeanBusCreateException(String message) {
        super(message);
    }

    public BeanBusCreateException(Throwable cause) {
        super(cause);
    }

    public BeanBusCreateException(String message, Throwable cause) {
        super(cause);
    }
}
