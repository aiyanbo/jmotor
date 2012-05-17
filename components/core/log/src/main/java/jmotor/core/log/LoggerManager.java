package jmotor.core.log;

import jmotor.core.log.log4j.Log4jLoggerProvider;
import jmotor.util.ClassUtils;

/**
 * Component:Log
 * Description:Logger manager
 * Date: 11-12-13
 *
 * @author Andy.Ai
 */
public class LoggerManager {
    private static final String loggerProviderClass = "jmotor.core.log.log4j.Log4jLoggerProvider";
    private static LoggerProvider loggerProvider;

    static {
        try {
            loggerProvider = (LoggerProvider) ClassUtils.newInstance(loggerProviderClass);
        } catch (Exception e) {
            loggerProvider = new Log4jLoggerProvider();
        }
    }

    private LoggerManager() {
    }

    public static Logger getLogger(String loggerName) {
        return loggerProvider.getLogger(loggerName);
    }

    public static Logger getLogger(Class<?> clazz) {
        return loggerProvider.getLogger(clazz);
    }

    public static Logger getRootLogger() {
        return loggerProvider.getRootLogger();
    }
}
