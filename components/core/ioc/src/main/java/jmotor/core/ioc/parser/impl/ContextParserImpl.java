package jmotor.core.ioc.parser.impl;

import jmotor.core.ioc.config.ElementConfiguration;
import jmotor.core.ioc.config.GlobalConfiguration;
import jmotor.core.ioc.config.PropertyConfiguration;
import jmotor.core.ioc.context.ContextPath;
import jmotor.core.ioc.dto.DocumentContextDto;
import jmotor.core.ioc.exception.ParseConfigurationException;
import jmotor.core.ioc.extractor.XmlContextExtractor;
import jmotor.core.ioc.extractor.impl.XmlContextExtractorImpl;
import jmotor.core.ioc.meta.DocumentQueue;
import jmotor.core.ioc.parser.ContextParser;
import jmotor.core.ioc.pool.BeanConfigurationPool;
import jmotor.core.ioc.repository.ContextRepository;
import jmotor.core.ioc.sax.handler.PropertiesNodeHandler;
import jmotor.util.BooleanUtils;
import jmotor.util.CollectionUtils;
import jmotor.util.StringUtils;
import jmotor.util.XmlUtils;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Component:IOC
 * Description:Context parser implementation
 * Date: 11-8-20
 *
 * @author Andy.Ai
 */
public class ContextParserImpl implements ContextParser {
    private BeanConfigurationPool beanConfigurationPool = ContextRepository.getRepository(BeanConfigurationPool.class);

    public DocumentContextDto parse(DocumentQueue queue) {
        DocumentContextDto documentContextDto = new DocumentContextDto();
        Element rootElement = queue.getDocument().getRootElement();
        XmlContextExtractor extractor = new XmlContextExtractorImpl(rootElement);
        GlobalConfiguration globalConfiguration = parseRootNode(rootElement);
        Collection<Node> propertiesNodes = extractor.extractNodes(ContextPath.PROPERTIES);
        if (CollectionUtils.isNotEmpty(propertiesNodes)) {
            for (Node propertiesNode : propertiesNodes) {
                DocumentContextDto propertiesContext = parsePropertiesNode(propertiesNode);
                documentContextDto.setProperties(propertiesContext.getProperties());
                documentContextDto.setEntryWrapperConfigurations(propertiesContext.getEntryWrapperConfigurations());
            }
        }
        Collection<Node> beanNodes = extractor.extractNodes(ContextPath.BEAN);
        if (CollectionUtils.isNotEmpty(beanNodes)) {
            List<ElementConfiguration> elementConfigurations = new ArrayList<ElementConfiguration>(beanNodes.size());
            for (Node beanNode : beanNodes) {
                ElementConfiguration elementConfiguration = parseBeanNode(beanNode, globalConfiguration, queue.getResources());
                elementConfigurations.add(elementConfiguration);
            }
            documentContextDto.setElementConfigurations(elementConfigurations);
        }
        return documentContextDto;
    }

    private GlobalConfiguration parseRootNode(Element rootElement) {
        XmlContextExtractor extractor = new XmlContextExtractorImpl(rootElement);
        String singleton = extractor.extractAttribute(ContextPath.SINGLETON);
        String autoWire = extractor.extractAttribute(ContextPath.AUTO_WIRE);
        String initMethod = extractor.extractAttribute(ContextPath.INIT_METHOD);
        GlobalConfiguration globalConfiguration = new GlobalConfiguration();
        globalConfiguration.setSingleton(BooleanUtils.valueOf(singleton));
        globalConfiguration.setAutoWire(autoWire);
        globalConfiguration.setInitMethod(initMethod);
        return globalConfiguration;
    }

    private DocumentContextDto parsePropertiesNode(Node propertiesNode) {
        PropertiesNodeHandler propertiesNodeHandler = new PropertiesNodeHandler();
        try {
            DocumentContextDto documentContextDto = new DocumentContextDto();
            XmlUtils.handleSAX(propertiesNode.asXML(), propertiesNodeHandler);
            documentContextDto.setProperties(propertiesNodeHandler.getResources());
            documentContextDto.setEntryWrapperConfigurations(propertiesNodeHandler.getEntryWrapperConfigurations());
            return documentContextDto;
        } catch (Exception e) {
            throw new ParseConfigurationException(e);
        }
    }

    private ElementConfiguration parseBeanNode(
            Node beanNode, GlobalConfiguration globalConfiguration, List<Properties> resources) {
        XmlContextExtractor extractor = new XmlContextExtractorImpl(beanNode);
        String id = extractor.extractAttribute(ContextPath.ID);
        String clazz = extractor.extractAttribute(ContextPath.CLASS);
        String parent = extractor.extractAttribute(ContextPath.PARENT);
        String abstraction = extractor.extractAttribute(ContextPath.ABSTRACT);
        String singleton = extractor.extractAttribute(ContextPath.SINGLETON);
        String autoWire = extractor.extractAttribute(ContextPath.AUTO_WIRE);
        String initMethod = extractor.extractAttribute(ContextPath.INIT_METHOD);
        List<PropertyConfiguration> properties = null;
        Collection<Node> propertyNodes = extractor.extractNodes(ContextPath.PROPERTY);
        if (CollectionUtils.isNotEmpty(propertyNodes)) {
            properties = new ArrayList<PropertyConfiguration>(propertyNodes.size());
            for (Node propertyNode : propertyNodes) {
                properties.add(parsePropertyNode(propertyNode, resources));
            }
        }
        ElementConfiguration elementConfiguration = new ElementConfiguration();
        elementConfiguration.setId(id);
        elementConfiguration.setClazz(clazz);
        elementConfiguration.setParent(parent);
        elementConfiguration.setAbstract(BooleanUtils.valueOf(abstraction));
        if (StringUtils.isNotBlank(singleton)) {
            elementConfiguration.setSingleton(Boolean.valueOf(singleton));
        } else {
            elementConfiguration.setSingleton(globalConfiguration.isSingleton());
        }
        if (StringUtils.isNotBlank(autoWire)) {
            elementConfiguration.setAutoWire(autoWire);
        } else {
            elementConfiguration.setAutoWire(globalConfiguration.getAutoWire());
        }
        if (StringUtils.isNotBlank(initMethod)) {
            elementConfiguration.setInitMethod(initMethod);
        } else {
            elementConfiguration.setInitMethod(globalConfiguration.getInitMethod());
        }
        elementConfiguration.setProperties(properties);
        beanConfigurationPool.put(id, beanNode.asXML());
        return elementConfiguration;
    }

    private PropertyConfiguration parsePropertyNode(Node propertyNode, List<Properties> resources) {
        XmlContextExtractor extractor = new XmlContextExtractorImpl(propertyNode);
        String name = extractor.extractAttribute(ContextPath.NAME);
        String value = extractor.extractAttribute(ContextPath.VALUE);
        String ref = null;
        if (value == null) {
            ref = extractor.extractAttribute(ContextPath.REF);
        } else if (value.matches("[$][{].*[}]")) {
            String key = value.substring(2, value.length() - 1);
            value = getResource(resources, key);
        }
        PropertyConfiguration propertyConfiguration = new PropertyConfiguration();
        propertyConfiguration.setName(name);
        propertyConfiguration.setValue(value);
        propertyConfiguration.setRef(ref);
        return propertyConfiguration;
    }

    private String getResource(List<Properties> resources, String key) {
        for (Properties resource : resources) {
            String value = resource.getProperty(key);
            if (StringUtils.isNotBlank(value)) {
                return value;
            }
        }
        return null;
    }
}
