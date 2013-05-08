package com.yunat.network.exception;

/**
 * Component:network-filter
 * Description:When the system can't found network filter configuration, it will be throws the exception.
 * Date: 12-8-21
 *
 * @author Andy.Ai
 */
public class NotFoundConfigurationException extends Exception {

    public NotFoundConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
