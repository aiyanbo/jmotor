package jmotor.core.ioc.context;

/**
 * Component:
 * Description:
 * Date: 11-8-22
 *
 * @author Andy.Ai
 */
public class BeanConfigurationContext {
    private String key;
    private String globalContext;
    private String context;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGlobalContext() {
        return globalContext;
    }

    public void setGlobalContext(String globalContext) {
        this.globalContext = globalContext;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
