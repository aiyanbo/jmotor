package jmotor.core.ioc.exception;

/**
 * Component:
 * Description:
 * Date: 11-8-20
 *
 * @author Andy.Ai
 */
public class ParseConfigurationException extends RuntimeException {
    public ParseConfigurationException() {
        super();
    }

    public ParseConfigurationException(String message) {
        super(message);
    }

    public ParseConfigurationException(Throwable cause) {
        super(cause);
    }

    public ParseConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
