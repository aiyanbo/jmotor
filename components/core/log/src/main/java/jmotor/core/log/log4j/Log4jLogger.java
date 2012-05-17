package jmotor.core.log.log4j;

import jmotor.core.log.Logger;

/**
 * Component:Log
 * Description:Log4j logger implementation
 * Date: 11-12-13
 *
 * @author Andy.Ai
 */
public class Log4jLogger implements Logger {
    private org.apache.log4j.Logger logger;

    public void trace(Object message) {
        logger.trace(message);
    }

    public void trace(Object message, Throwable throwable) {
        logger.trace(message, throwable);
    }

    public void debug(Object message) {
        logger.debug(message);
    }

    public void debug(Object message, Throwable throwable) {
        logger.debug(message, throwable);
    }

    public void info(Object message) {
        logger.info(message);
    }

    public void info(Object message, Throwable throwable) {
        logger.info(message, throwable);
    }

    public void warning(Object message) {
        logger.warn(message);
    }

    public void warning(Object message, Throwable throwable) {
        logger.warn(message, throwable);
    }

    public void error(Object message) {
        logger.error(message);
    }

    public void error(Object message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public void fatal(Object message) {
        logger.fatal(message);
    }

    public void fatal(Object message, Throwable throwable) {
        logger.fatal(message, throwable);
    }

    public void setLogger(org.apache.log4j.Logger logger) {
        this.logger = logger;
    }
}
