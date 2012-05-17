package jmotor.core.ioc.parser.impl;

import jmotor.core.ioc.config.ImportConfiguration;
import jmotor.core.ioc.context.ApplicationContextConstant;
import jmotor.core.ioc.context.ContextPath;
import jmotor.core.ioc.exception.ContextLoaderException;
import jmotor.core.ioc.extractor.XmlContextExtractor;
import jmotor.core.ioc.extractor.impl.XmlContextExtractorImpl;
import jmotor.core.ioc.loader.ContextLoaderAdapter;
import jmotor.core.ioc.meta.Context;
import jmotor.core.ioc.meta.DocumentQueue;
import jmotor.core.ioc.parser.ContextQueueParser;
import jmotor.core.ioc.type.ResourceType;
import jmotor.util.CollectionUtils;
import jmotor.util.StreamUtils;
import jmotor.util.StringUtils;
import jmotor.util.XmlUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Component:
 * Description:
 * Date: 11-8-23
 *
 * @author Andy.Ai
 */
public class ContextQueueParserImpl implements ContextQueueParser {
    private ContextLoaderAdapter contextLoaderAdapter;

    public List<DocumentQueue> loadQueue(Document document) throws ContextLoaderException {
        List<DocumentQueue> queues = new ArrayList<DocumentQueue>();
        loadContextQueue(queues, document);
        return queues;
    }

    private void loadContextQueue(List<DocumentQueue> queues, Document document) throws ContextLoaderException {
        XmlContextExtractor xmlContextExtractor = new XmlContextExtractorImpl(document.getRootElement());
        Collection<Node> importNodes = xmlContextExtractor.extractNodes(ContextPath.IMPORT);
        if (CollectionUtils.isNotEmpty(importNodes)) {
            for (Node importNode : importNodes) {
                XmlContextExtractor importNodeExtractor = new XmlContextExtractorImpl(importNode);
                String src = importNodeExtractor.extractAttribute(ContextPath.SRC);
                Context context = contextLoaderAdapter.loadContext(src);
                DocumentQueue documentQueue = new DocumentQueue();
                documentQueue.setType(context.getType());
                documentQueue.setPath(context.getPath());
                try {
                    documentQueue.setDocument(XmlUtils.loadDocument(context.getData()));
                    documentQueue.setResources(loadPropertiesList(documentQueue.getDocument()));
                } catch (DocumentException e) {
                    throw new ContextLoaderException("Can't load document:(" + src + ")");
                }
                if (!queues.contains(documentQueue)) {
                    queues.add(0, documentQueue);
                }
                loadContextQueue(queues, documentQueue.getDocument());
            }
        }
        DocumentQueue mainDocumentQueue = new DocumentQueue();
        mainDocumentQueue.setType(ResourceType.MAIN_CONTEXT);
        mainDocumentQueue.setDocument(document);
        mainDocumentQueue.setResources(loadPropertiesList(document));
        queues.add(mainDocumentQueue);
    }

    private List<Properties> loadPropertiesList(Document document) throws ContextLoaderException {
        XmlContextExtractor xmlContextExtractor = new XmlContextExtractorImpl(document.getRootElement());
        Collection<Node> propertiesNodes = xmlContextExtractor.extractNodes(ContextPath.PROPERTIES);
        List<Properties> propertiesList = null;
        if (CollectionUtils.isNotEmpty(propertiesNodes)) {
            propertiesList = new ArrayList<Properties>();
            for (Node propertiesNode : propertiesNodes) {
                XmlContextExtractor propertiesNodeExtractor = new XmlContextExtractorImpl(propertiesNode);
                String src = propertiesNodeExtractor.extractAttribute(ContextPath.SRC);
                if (StringUtils.isNotBlank(src)) {
                    Context context = contextLoaderAdapter.loadContext(src);
                    try {
                        propertiesList.add(StreamUtils.loadProperties(context.getData()));
                    } catch (IOException e) {
                        throw new ContextLoaderException("Can't load properties:(" + src + ")");
                    }
                }
            }
        }
        return propertiesList;
    }

    private ImportConfiguration parseImportNode(Node importNode) {
        XmlContextExtractor extractor = new XmlContextExtractorImpl(importNode);
        String src = extractor.extractAttribute(ContextPath.SRC);
        ImportConfiguration importConfiguration = new ImportConfiguration();
        importConfiguration.setPath(src);
        return importConfiguration;
    }

    public void setContextLoaderAdapter(ContextLoaderAdapter contextLoaderAdapter) {
        this.contextLoaderAdapter = contextLoaderAdapter;
    }
}
