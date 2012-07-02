package jmotor.core.ioc.sax.handler;

import jmotor.core.ioc.config.EntryConfiguration;
import jmotor.core.ioc.config.EntryWrapperConfiguration;
import jmotor.core.ioc.context.ContextPath;
import jmotor.util.CollectionUtils;
import jmotor.util.ObjectUtils;
import jmotor.util.XmlUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Component:IOC
 * Description:Properties node handler
 * Date: 11-11-19
 *
 * @author Andy.Ai
 */
public class PropertiesNodeHandler extends DefaultHandler {
    private static final String[] COLLECTION_Q_NAMES = new String[]{
            ContextPath.MAP,
            ContextPath.SET,
            ContextPath.LIST,
            ContextPath.ARRAY
    };
    private Map<String, String> resources = new HashMap<String, String>();
    private List<EntryWrapperConfiguration> entryWrapperConfigurations = new ArrayList<EntryWrapperConfiguration>();
    private EntryWrapperConfiguration currentEntryWrapperConfiguration;
    private boolean scanText = false;
    private String currentQName;

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentQName = qName;
        if (CollectionUtils.contains(COLLECTION_Q_NAMES, qName)) {
            currentEntryWrapperConfiguration = new EntryWrapperConfiguration();
            currentEntryWrapperConfiguration.setCategory(qName);
            XmlUtils.fillProperties(currentEntryWrapperConfiguration, attributes);
            entryWrapperConfigurations.add(currentEntryWrapperConfiguration);
        } else if (ContextPath.ENTRY.equals(qName)) {
            EntryConfiguration entryConfiguration = new EntryConfiguration();
            XmlUtils.fillProperties(entryConfiguration, attributes);
            List<EntryConfiguration> entryConfigurations = currentEntryWrapperConfiguration.getEntries();
            if (entryConfigurations == null) {
                entryConfigurations = new ArrayList<EntryConfiguration>();
            }
            entryConfigurations.add(entryConfiguration);
            currentEntryWrapperConfiguration.setEntries(entryConfigurations);
        } else if (ObjectUtils.isDifferent(ContextPath.PROPERTIES, qName)) {
            scanText = true;
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        scanText = false;
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (scanText) {
            resources.put(currentQName, new String(ch, start, length));
        }
    }

    public Map<String, String> getResources() {
        return resources;
    }

    public List<EntryWrapperConfiguration> getEntryWrapperConfigurations() {
        return entryWrapperConfigurations;
    }
}
