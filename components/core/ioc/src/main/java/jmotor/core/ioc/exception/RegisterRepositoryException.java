package jmotor.core.ioc.exception;

/**
 * Component:
 * Description:
 * Date: 12-2-26
 *
 * @author Andy.Ai
 */
public class RegisterRepositoryException extends RuntimeException {
    public RegisterRepositoryException() {
    }

    public RegisterRepositoryException(String message) {
        super(message);
    }

    public RegisterRepositoryException(Throwable cause) {
        super(cause);
    }

    public RegisterRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
