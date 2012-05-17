package jmotor.core.ioc.loader;

import jmotor.core.ioc.exception.ContextLoaderException;
import jmotor.core.ioc.meta.Context;
import jmotor.core.ioc.meta.DocumentQueue;
import org.dom4j.Document;

/**
 * Component:
 * Description:
 * Date: 11-8-23
 *
 * @author Andy.Ai
 */
public interface ContextLoaderAdapter {
    Context loadContext(String src) throws ContextLoaderException;
}
