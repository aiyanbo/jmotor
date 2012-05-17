package jmotor.core.ioc.impl;

import jmotor.core.ioc.BeanBusManager;
import jmotor.core.ioc.config.ElementConfiguration;
import jmotor.core.ioc.config.PropertyConfiguration;
import jmotor.core.ioc.context.ApplicationContextConstant;
import jmotor.core.ioc.dto.DocumentContextDto;
import jmotor.core.ioc.exception.BeanBusCreateException;
import jmotor.core.ioc.exception.BeanCreateException;
import jmotor.core.ioc.exception.ContextLoaderException;
import jmotor.core.ioc.meta.DocumentQueue;
import jmotor.core.ioc.meta.Element;
import jmotor.core.ioc.parser.ContextParser;
import jmotor.core.ioc.parser.ContextQueueParser;
import jmotor.core.ioc.pool.ElementConfigurationPool;
import jmotor.core.ioc.pool.ElementPool;
import jmotor.core.ioc.pool.EntryWrapperConfigurationPool;
import jmotor.core.ioc.pool.PropertiesPool;
import jmotor.core.ioc.repository.ContextRepository;
import jmotor.core.ioc.util.ReflectionUtils;
import jmotor.util.CollectionUtils;
import jmotor.util.StringUtils;
import jmotor.util.converter.SimpleValueConverter;
import org.dom4j.Document;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Component:IOC
 * Description:Simple bean bus manager implementation
 * Date: 11-8-18
 *
 * @author Andy.Ai
 */
public class BeanBusManagerImpl implements BeanBusManager {
    private ContextParser contextParser;
    private ContextQueueParser contextQueueParser;
    private ElementPool elementPool = ContextRepository.getRepository(ElementPool.class);
    private PropertiesPool propertiesPool = ContextRepository.getRepository(PropertiesPool.class);
    private ElementConfigurationPool elementConfigPool = ContextRepository.getRepository(ElementConfigurationPool.class);
    private EntryWrapperConfigurationPool entryWrapperConfigPool = ContextRepository.getRepository(EntryWrapperConfigurationPool.class);


    @SuppressWarnings("unchecked")
    public <T> T getBean(String id) {
        Object bean = elementPool.get(id);
        if (bean == null) {
            bean = createObject(id);
        }
        return (T) bean;
    }

    public void autoWire(Object bean) {
        fillPropertiesByName(bean);
    }

    @SuppressWarnings("unchecked")
    public <T> T createBean(ElementConfiguration elementConfiguration) {
        return (T) createObject(elementConfiguration);
    }

    private Object createObject(String id) {
        ElementConfiguration elementConfiguration = loadElementConfiguration(id);
        if (elementConfiguration != null) {
            return createObject(elementConfiguration);
        }
        return null;
    }

    private Object createObject(ElementConfiguration elementConfiguration) {
        Object bean;
        try {
            bean = ReflectionUtils.newInstance(elementConfiguration.getClazz());
            fillProperties(bean, elementConfiguration);
            if (StringUtils.isNotBlank(elementConfiguration.getInitMethod())) {
                ReflectionUtils.invoke(bean, elementConfiguration.getInitMethod());
            }
        } catch (Exception e) {
            throw new BeanCreateException("bean id:" + elementConfiguration.getId() + " create failure!", e);
        }
        if (elementConfiguration.isSingleton()) {
            Element element = new Element();
            element.setKey(elementConfiguration.getId());
            element.setValue(bean);
            elementPool.put(element);
        }
        return bean;
    }

    private void fillProperties(Object bean, ElementConfiguration elementConfiguration) {
        String autoWire = elementConfiguration.getAutoWire();
        if (StringUtils.isBlank(autoWire)) {
            fillPropertiesByConfig(bean, elementConfiguration);
        } else if (ApplicationContextConstant.AUTO_WIRE_BY_NAME.equalsIgnoreCase(autoWire)) {
            fillPropertiesByName(bean);
        } else if (ApplicationContextConstant.AUTO_WIRE_BY_TYPE.equalsIgnoreCase(autoWire)) {
            fillPropertiesByType(bean);
        }
    }

    private void fillPropertiesByName(Object bean) {
        PropertyDescriptor[] properties = ReflectionUtils.getPropertyDescriptors(bean);
        for (PropertyDescriptor property : properties) {
            String propertyName = property.getName();
            Object value = getBean(propertyName);
            if (value != null) {
                ReflectionUtils.setPropertyValue(bean, propertyName, value);
            }
        }
    }

    private void fillPropertiesByType(Object bean) {
        PropertyDescriptor[] properties = ReflectionUtils.getPropertyDescriptors(bean);
        for (PropertyDescriptor property : properties) {
            ElementConfiguration elementConfiguration = loadElementConfiguration(property.getPropertyType());
            Object value = null;
            if (elementConfiguration != null) {
                value = createObject(elementConfiguration);
            }
            ReflectionUtils.setPropertyValue(bean, property.getName(), value);
        }
    }

    private void fillPropertiesByConfig(Object bean, ElementConfiguration elementConfiguration) {
        List<PropertyConfiguration> properties = retrieveProperties(elementConfiguration);
        for (PropertyConfiguration property : properties) {
            String propertyName = property.getName();
            if (StringUtils.isNotBlank(property.getValue())) {
                Class<?> propertyType = ReflectionUtils.getPropertyType(bean, propertyName);
                Object value = SimpleValueConverter.convert(propertyType, property.getValue());
                ReflectionUtils.setPropertyValue(bean, propertyName, value);
            } else if (StringUtils.isNotBlank(property.getRef())) {
                ReflectionUtils.setPropertyValue(bean, propertyName, getBean(property.getRef()));
            }
        }
    }

    private List<PropertyConfiguration> retrieveProperties(ElementConfiguration elementConfiguration) {
        List<PropertyConfiguration> properties = new ArrayList<PropertyConfiguration>();
        if (StringUtils.isNotBlank(elementConfiguration.getParent())) {
            retrieveParentProperties(elementConfiguration, properties);
        }
        properties.addAll(elementConfiguration.getProperties());
        return properties;
    }

    private void retrieveParentProperties(ElementConfiguration elementConfiguration, List<PropertyConfiguration> properties) {
        ElementConfiguration parentConfiguration = loadElementConfiguration(elementConfiguration.getParent());
        List<PropertyConfiguration> parentProperties = parentConfiguration.getProperties();
        if (CollectionUtils.isNotEmpty(parentProperties)) {
            properties.addAll(0, parentProperties);
        }
        if (StringUtils.isNotBlank(parentConfiguration.getParent())) {
            retrieveParentProperties(parentConfiguration, properties);
        }
    }

    private ElementConfiguration loadElementConfiguration(Class<?> clazz) {
        return elementConfigPool.getByClassName(clazz.getName());
    }

    private ElementConfiguration loadElementConfiguration(String key) {
        return elementConfigPool.get(key);
    }

    public void initialization(Document document) {
        try {
            List<DocumentQueue> queues = contextQueueParser.loadQueue(document);
            for (DocumentQueue documentQueue : queues) {
                DocumentContextDto documentContextDto = contextParser.parse(documentQueue);
                if (CollectionUtils.isNotEmpty(documentContextDto.getElementConfigurations())) {
                    elementConfigPool.putAll(documentContextDto.getElementConfigurations());
                }
                if (CollectionUtils.isNotEmpty(documentContextDto.getProperties())) {
                    propertiesPool.putAll(documentContextDto.getProperties());
                }
                if (CollectionUtils.isNotEmpty(documentContextDto.getEntryWrapperConfigurations())) {
                    entryWrapperConfigPool.putAll(documentContextDto.getEntryWrapperConfigurations());
                }
            }
        } catch (ContextLoaderException e) {
            throw new BeanBusCreateException(e);
        }
    }

    public void setContextQueueParser(ContextQueueParser contextQueueParser) {
        this.contextQueueParser = contextQueueParser;
    }

    public void setContextParser(ContextParser contextParser) {
        this.contextParser = contextParser;
    }
}
