package jmotor.core.ioc.exception;

/**
 * Component:
 * Description:
 * Date: 11-8-19
 *
 * @author Andy.Ai
 */
public class ContextLoaderException extends Exception {
    public ContextLoaderException() {
        super();
    }

    public ContextLoaderException(String message) {
        super(message);
    }

    public ContextLoaderException(Throwable cause) {
        super(cause);
    }

    public ContextLoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
