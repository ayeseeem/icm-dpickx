package org.ayeseeem.dpick.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Example XML for use in integration tests and examples
 *
 * @author ayeseeem@gmail.com
 *
 */
public class XmlExampleFixture {

    protected final Node eg = createExampleDom();

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Node createExampleDom() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new Error("XML exception - translated to error", e);
        }
        Document doc = builder.newDocument();

        Element root = doc.createElement("RootElement");
        doc.appendChild(root);

        root.appendChild(doc.createElement("SomethingUnique"));

        root.appendChild(doc.createElement("Repeated"));
        root.appendChild(doc.createElement("Repeated"));

        {
            Element elementWithAttribute = doc.createElement("ElementWithSizeAttribute");
            elementWithAttribute.setAttribute("size", "15");
            root.appendChild(elementWithAttribute);
        }

        {
            Element element = doc.createElement("ContainsSeventeen");
            element.setTextContent("17");
            root.appendChild(element);
        }

        {
            Element element = doc.createElement("ContainsAttributeWithEighteen");
            element.setTextContent("blah blah");
            element.setAttribute("attrOf18", "18");
            root.appendChild(element);
        }

        {
            Element element = doc.createElement("Duplicate");
            element.setTextContent("123");
            root.appendChild(element);
        }
        {
            Element element = doc.createElement("Duplicate");
            element.setTextContent("123");
            root.appendChild(element);
        }

        {
            Element element = doc.createElement("DuplicateEleDiffContent");
            element.setTextContent("123");
            root.appendChild(element);
        }
        {
            Element element = doc.createElement("DuplicateEleDiffContent");
            element.setTextContent("456");
            root.appendChild(element);
        }

        {
            Element element = doc.createElement("AlwaysTrue");
            element.setTextContent("true");
            root.appendChild(element);
        }

        safeDumpXmlToConsole(doc);

        return doc.getDocumentElement();
    }

    private void safeDumpXmlToConsole(Document doc) {
        try {
            dumpXmlToConsole(doc);
        } catch (TransformerFactoryConfigurationError | TransformerException e) {
            logger.error("Problem dumping XML", e);
        }
    }

    private void dumpXmlToConsole(Document doc)
            throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult console = new StreamResult(System.out);
        transformer.transform(source, console);
    }
}
