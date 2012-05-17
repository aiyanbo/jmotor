package jmotor.plugin.hibernate.exception;

/**
 * Component:Hibernate-plugin
 * Description:Result transform exception
 * Date: 12-3-20
 *
 * @author Andy.Ai
 */
public class ResultTransformException extends RuntimeException {
    public ResultTransformException() {
        super();
    }

    public ResultTransformException(String message) {
        super(message);
    }

    public ResultTransformException(Throwable cause) {
        super(cause);
    }

    public ResultTransformException(String message, Throwable cause) {
        super(message, cause);
    }
}
