package jmotor.core.ioc.parser;

import jmotor.core.ioc.dto.DocumentContextDto;
import jmotor.core.ioc.exception.ParseConfigurationException;
import jmotor.core.ioc.meta.DocumentQueue;

/**
 * Component:IOC
 * Description:Context parser
 * Date: 11-8-19
 *
 * @author Andy.Ai
 */
public interface ContextParser {
    DocumentContextDto parse(DocumentQueue queue) throws ParseConfigurationException;
}
