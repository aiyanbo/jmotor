package jmotor.core.log.log4j;

import jmotor.core.log.Logger;
import jmotor.core.log.LoggerProvider;

/**
 * Component:Log
 * Description:Log4j logger provider implementation
 * Date: 11-12-13
 *
 * @author Andy.Ai
 */
public class Log4jLoggerProvider implements LoggerProvider {
    public Logger getLogger(String loggerName) {
        Log4jLogger logger = new Log4jLogger();
        logger.setLogger(org.apache.log4j.Logger.getLogger(loggerName));
        return logger;
    }

    public Logger getLogger(Class<?> clazz) {
        Log4jLogger logger = new Log4jLogger();
        logger.setLogger(org.apache.log4j.Logger.getLogger(clazz));
        return logger;
    }

    public Logger getRootLogger() {
        Log4jLogger logger = new Log4jLogger();
        logger.setLogger(org.apache.log4j.Logger.getRootLogger());
        return logger;
    }
}
