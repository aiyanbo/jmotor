package jmotor.util;

import jmotor.util.converter.SimpleValueConverter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;

/**
 * Component:Utility
 * Description:Xml utilities
 * Date: 11-8-20
 *
 * @author Andy.Ai
 */
public class XmlUtils {
    private static SAXReader reader;

    private XmlUtils() {
    }

    public static String getAttribute(Node node, String xpathExpression) {
        Node attributeNode = node.selectSingleNode(transformAttributePattern(xpathExpression));
        return attributeNode == null ? null : attributeNode.getText();
    }

    public static String transformAttributePattern(String xpathExpression) {
        String _xpathExpression = xpathExpression;
        if (_xpathExpression.indexOf('@') == -1) {
            _xpathExpression = '@' + _xpathExpression;
        }
        return _xpathExpression;
    }

    public static Document loadDocument(InputStream inputStream) throws DocumentException {
        return getReader().read(inputStream);
    }

    public static void fillProperties(Object object, Attributes attributes) {
        PropertyDescriptor[] propertyDescriptors = ObjectUtils.getPropertyDescriptors(object);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String attributeValue = attributes.getValue(propertyDescriptor.getName());
            if (StringUtils.isNotBlank(attributeValue)) {
                Object value = SimpleValueConverter.convert(propertyDescriptor.getPropertyType(), attributeValue);
                if (value != null) {
                    ObjectUtils.setPropertyValue(object, propertyDescriptor.getName(), value);
                }
            }
        }
    }

    public static void handleSAX(String xmlContent, String charset, DefaultHandler saxHandler)
            throws IOException, SAXException, ParserConfigurationException {
        handleSAX(StreamUtils.transform(xmlContent, charset), saxHandler);
    }

    public static void handleSAX(String xmlContent, DefaultHandler saxHandler)
            throws IOException, SAXException, ParserConfigurationException {
        handleSAX(StreamUtils.transform(xmlContent), saxHandler);
    }

    public static void handleSAX(InputStream inputStream, DefaultHandler saxHandler)
            throws ParserConfigurationException, SAXException, IOException {
//        SAXParserFactory factory = SAXParserFactory.newInstance();
//        factory.setValidating(false);
//        SAXParser saxParser = factory.newSAXParser();
//        saxParser.parse(inputStream, saxHandler);
        InputSource inputSource = new InputSource(inputStream);
        handleSAX(inputSource, saxHandler);
    }

    public static void handleSAX(InputSource inputSource, DefaultHandler saxHandler)
            throws SAXException, IOException {
        XMLReader reader = XMLReaderFactory.createXMLReader();
        reader.setContentHandler(saxHandler);
        reader.parse(inputSource);
    }

    private static SAXReader getReader() {
        if (reader == null) {
            reader = new SAXReader();
        }
        return reader;
    }
}
