package jmotor.core.ioc.dto;

import jmotor.core.ioc.config.ElementConfiguration;
import jmotor.core.ioc.config.EntryWrapperConfiguration;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Component:
 * Description:
 * Date: 11-11-14
 *
 * @author Andy.Ai
 */
public class DocumentContextDto implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private List<ElementConfiguration> elementConfigurations;
    private Map<String, String> properties;
    private List<EntryWrapperConfiguration> entryWrapperConfigurations;

    public List<ElementConfiguration> getElementConfigurations() {
        return elementConfigurations;
    }

    public void setElementConfigurations(List<ElementConfiguration> elementConfigurations) {
        this.elementConfigurations = elementConfigurations;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public List<EntryWrapperConfiguration> getEntryWrapperConfigurations() {
        return entryWrapperConfigurations;
    }

    public void setEntryWrapperConfigurations(List<EntryWrapperConfiguration> entryWrapperConfigurations) {
        this.entryWrapperConfigurations = entryWrapperConfigurations;
    }
}
