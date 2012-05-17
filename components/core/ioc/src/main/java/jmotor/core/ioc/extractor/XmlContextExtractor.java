package jmotor.core.ioc.extractor;

import org.dom4j.Node;

import java.util.Collection;

/**
 * Component:
 * Description:
 * Date: 11-8-20
 *
 * @author Andy.Ai
 */
public interface XmlContextExtractor {
    String extractAttribute(String xpathExpression);

    Node extractNode(String xpathExpression);

    Collection<Node> extractNodes(String xpathExpression);
}
