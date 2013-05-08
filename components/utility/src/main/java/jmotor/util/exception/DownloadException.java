package jmotor.util.exception;

/**
 * Component:
 * Description:
 * Date: 13-5-8
 *
 * @author Andy Ai
 */
public class DownloadException extends RuntimeException {
    public DownloadException(String message) {
        super(message);
    }

    public DownloadException(String message, Throwable cause) {
        super(message, cause);
    }
}
