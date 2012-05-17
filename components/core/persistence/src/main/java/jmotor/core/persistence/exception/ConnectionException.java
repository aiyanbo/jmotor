package jmotor.core.persistence.exception;

/**
 * Component:
 * Description:
 * Date: 11-9-25
 *
 * @author Andy.Ai
 */
public class ConnectionException extends RuntimeException {
    public ConnectionException() {
        super();
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(Throwable cause) {
        super(cause);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
