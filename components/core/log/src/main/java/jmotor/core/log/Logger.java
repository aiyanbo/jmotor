package jmotor.core.log;

/**
 * Component:Log
 * Description:Logger
 * Date: 11-12-13
 *
 * @author Andy.Ai
 */
public interface Logger {
    void trace(Object message);

    void trace(Object message, Throwable throwable);

    void debug(Object message);

    void debug(Object message, Throwable throwable);

    void info(Object message);

    void info(Object message, Throwable throwable);

    void warning(Object message);

    void warning(Object message, Throwable throwable);

    void error(Object message);

    void error(Object message, Throwable throwable);

    void fatal(Object message);

    void fatal(Object message, Throwable throwable);
}
