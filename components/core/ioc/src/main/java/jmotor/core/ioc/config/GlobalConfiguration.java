package jmotor.core.ioc.config;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 11-8-20
 *
 * @author Andy.Ai
 */
public class GlobalConfiguration implements Serializable {
    private static final long serialVersionUID = -6849794470754667710L;
    private boolean isSingleton;
    private String autoWire;
    private String initMethod;

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }

    public String getAutoWire() {
        return autoWire;
    }

    public void setAutoWire(String autoWire) {
        this.autoWire = autoWire;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }
}
