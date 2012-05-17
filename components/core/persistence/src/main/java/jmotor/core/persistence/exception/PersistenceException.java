package jmotor.core.persistence.exception;

/**
 * Component:
 * Description:
 * Date: 11-9-22
 *
 * @author Andy.Ai
 */
public class PersistenceException extends RuntimeException {
    public PersistenceException() {
        super();
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
