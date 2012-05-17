package jmotor.core.persistence.exception;

/**
 * Component:
 * Description:
 * Date: 12-4-12
 *
 * @author Andy.Ai
 */
public class EntityMappingException extends RuntimeException {
    public EntityMappingException() {
        super();
    }

    public EntityMappingException(String message) {
        super(message);
    }

    public EntityMappingException(Throwable cause) {
        super(cause);
    }

    public EntityMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
