package jmotor.web.network.filter;

import com.yunat.network.exception.NotFoundConfigurationException;

/**
 * Component:network-filter
 * Description:Network JMX bean
 * Date: 12-8-21
 *
 * @author Andy.Ai
 */
public interface NetworkFilterMBean {
    /**
     * fire change the network filter configuration
     *
     * @throws com.yunat.network.exception.NotFoundConfigurationException
     *
     */
    void fireChangeConfiguration() throws NotFoundConfigurationException;
}
