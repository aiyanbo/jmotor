package jmotor.core.ioc.repository.impl;

import jmotor.core.ioc.config.ElementConfiguration;
import jmotor.core.ioc.config.EntryWrapperConfiguration;
import jmotor.core.ioc.context.BeanConfigurationContext;
import jmotor.core.ioc.meta.Element;
import jmotor.core.ioc.pool.BeanConfigurationPool;
import jmotor.core.ioc.pool.ElementConfigurationPool;
import jmotor.core.ioc.pool.ElementPool;
import jmotor.core.ioc.pool.EntryWrapperConfigurationPool;
import jmotor.core.ioc.pool.PropertiesPool;
import jmotor.core.ioc.pool.impl.BeanConfigurationCachePoolImpl;
import jmotor.core.ioc.pool.impl.ElementConfigurationPoolImpl;
import jmotor.core.ioc.pool.impl.ElementPoolImpl;
import jmotor.core.ioc.pool.impl.EntryWrapperConfigurationPoolImpl;
import jmotor.core.ioc.pool.impl.PropertiesPoolImpl;

import java.util.Properties;

/**
 * Component:IOC
 * Description:Default context repository
 * Date: 12-1-14
 *
 * @author Andy.Ai
 */
@Deprecated
public class DefaultContextRepository {
    private BeanConfigurationPool beanConfigPool;
    private ElementPool elePool;
    private ElementConfigurationPool eleConfigPool;
    private EntryWrapperConfigurationPool entryWrapperConfigPool;
    private PropertiesPool propertiesPool;

    public DefaultContextRepository() {
        init();
    }

    private void init() {
        beanConfigPool = new BeanConfigurationCachePoolImpl();
        elePool = new ElementPoolImpl();
        eleConfigPool = new ElementConfigurationPoolImpl();
        entryWrapperConfigPool = new EntryWrapperConfigurationPoolImpl();
        propertiesPool = new PropertiesPoolImpl();
    }

    public void put(Object object) {
        if (object instanceof BeanConfigurationContext) {
            beanConfigPool.put((BeanConfigurationContext) object);
        } else if (object instanceof Element) {
            elePool.put((Element) object);
        } else if (object instanceof ElementConfiguration) {
            eleConfigPool.put((ElementConfiguration) object);
        } else if (object instanceof EntryWrapperConfiguration) {
            entryWrapperConfigPool.put((EntryWrapperConfiguration) object);
        } else if (object instanceof Properties) {
            propertiesPool.put((Properties) object);
        }
    }
}
