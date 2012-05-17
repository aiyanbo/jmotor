package jmotor.core.ioc.extractor.impl;

import jmotor.core.ioc.extractor.XmlContextExtractor;
import jmotor.util.XmlUtils;
import org.dom4j.Node;

import java.util.Collection;

/**
 * Component:
 * Description:
 * Date: 11-8-20
 *
 * @author Andy.Ai
 */
public class XmlContextExtractorImpl implements XmlContextExtractor {
    private Node node;

    public XmlContextExtractorImpl(Node node) {
        this.node = node;
    }

    public String extractAttribute(String xpathExpression) {
        return XmlUtils.getAttribute(node, xpathExpression);
    }

    public Node extractNode(String xpathExpression) {
        return node.selectSingleNode(xpathExpression);
    }

    @SuppressWarnings("unchecked")
    public Collection<Node> extractNodes(String xpathExpression) {
        return node.selectNodes(xpathExpression);
    }

    public Node getNode() {
        return node;
    }
}
