package jmotor.util.exception;

/**
 * Component:
 * Description:
 * Date: 12-11-6
 *
 * @author Andy.Ai
 */
public class SqlGenerateException extends RuntimeException {
    public SqlGenerateException(String message) {
        super(message);
    }

    public SqlGenerateException(Throwable cause) {
        super(cause);
    }

    public SqlGenerateException(String message, Throwable cause) {
        super(message, cause);
    }
}
