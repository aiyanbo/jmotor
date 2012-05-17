package jmotor.core.log;


/**
 * Component:Log
 * Description:Logger provider
 * Date: 11-12-13
 *
 * @author Andy.Ai
 */
public interface LoggerProvider {
    Logger getLogger(String loggerName);

    Logger getLogger(Class<?> clazz);

    Logger getRootLogger();
}
