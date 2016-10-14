package org.ayeseeem.dpick.xml;

import static org.ayeseeem.dpick.xml.NodeMatchers.xpath;

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
import javax.xml.xpath.XPathExpressionException;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Examples of usage, mainly as JUnit tests
 *
 * @author ayeseeem@gmail.com
 *
 */
public class Examples {

    /**
     * Untested example of the sort of usage
     *
     * @param node
     *            DOM Node within which to check - usually a document root
     */
    public void exampleProcessNode(Node node) {
        try {
            XmlDocumentChecker.check(node).andExpect(xpath("/Request/Attributes").exists())
                    .andExpect(xpath("//Attribute").exists())
                    .andExpect(xpath("//Attribute").nodeCount(2))
                    .andExpect(xpath("/NonExistent").doesNotExist())
                    .andExpect(xpath("/Attribute").doesNotExist()); // not a top-level element
        } catch (XPathExpressionException | AssertionError e) {
            throw new RuntimeException("dpickx: problem with XPath", e);
        }
    }

    @Test
    public void expect_Exists_RootElement() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("/RootElement").exists());
    }

    @Test
    public void expect_DoesNotExist() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("/NeverExisting").doesNotExist());
    }

    @Test
    public void expect_Exists_ButDoesNotExist() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("XPath /NeverExisting does not exist");

        XmlDocumentChecker.check(eg).andExpect(xpath("/NeverExisting").exists());
    }

    @Test
    public void expect_DoesNotExist_ButDoesExist() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("XPath /RootElement exists");

        XmlDocumentChecker.check(eg).andExpect(xpath("/RootElement").doesNotExist());
    }

    @Test
    public void expect_Exists_SubElement_ByFullPath() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("/RootElement/SomethingUnique").exists());
    }

    @Test
    public void expect_Exists_SubElement_ByPartialPath() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//SomethingUnique").exists());
    }

    @Test
    public void expect_Exists_Attribute() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//ElementWithSizeAttribute/@size").exists());
        XmlDocumentChecker.check(eg).andExpect(xpath("/RootElement/ElementWithSizeAttribute/@size").exists());
    }

    @Test
    public void expect_DoesNotExist_Attribute() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//ElementWithSizeAttribute/@nonExistentAttribute").doesNotExist());
        XmlDocumentChecker.check(eg).andExpect(xpath("/RootElement/ElementWithSizeAttribute/@nonExistentAttribute").doesNotExist());
    }

    @Test
    public void expect_NodeCount() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//Repeated").nodeCount(2));
    }

    @Test
    public void expect_NodeCount_OfZero_ForNonExistentElement() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//NeverExisting").nodeCount(0));
    }

    @Test
    public void expect_NodeCount_Wrong() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected 888 nodes for XPath //Repeated, not 2");

        XmlDocumentChecker.check(eg).andExpect(xpath("//Repeated").nodeCount(888));
    }

    @Test
    public void expect_NodeCount_Matcher() throws XPathExpressionException {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Not implemented yet");

        XmlDocumentChecker.check(eg).andExpect(xpath("//Repeated").nodeCount(null));
    }

    @Test
    public void expect_Node_Matcher() throws XPathExpressionException {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Not implemented yet");

        XmlDocumentChecker.check(eg).andExpect(xpath("//Repeated").node(null));
    }

    @Test
    public void expect_Number() throws XPathExpressionException {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Not implemented yet");

        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsSeventeen").number(17.0));
    }

    @Test
    public void expect_Number_Matcher() throws XPathExpressionException {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Not implemented yet");

        Matcher<? super Double> dummyMatcher = null;
        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsSeventeen").number(dummyMatcher));
    }

    @Test
    public void expect_String() throws XPathExpressionException {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Not implemented yet");

        XmlDocumentChecker.check(eg).andExpect(xpath("//Repeated").string("I'm a string"));
    }

    @Test
    public void expect_String_Matcher() throws XPathExpressionException {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Not implemented yet");

        Matcher<? super String> dummyMatcher = null;
        XmlDocumentChecker.check(eg).andExpect(xpath("//Repeated").string(dummyMatcher));
    }

    @Test
    public void invalidXpath() throws XPathExpressionException {
        thrown.expect(XPathExpressionException.class);
        thrown.expectCause(IsInstanceOf.<Throwable>instanceOf(TransformerException.class));

        XmlDocumentChecker.check(eg).andExpect(xpath("This is an invalid XPath").exists());
    }

    @Test
    public void expect_BooleanValue() throws XPathExpressionException {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Not implemented yet");

        XmlDocumentChecker.check(eg).andExpect(xpath("/AlwaysTrue").booleanValue(true));
    }


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Node eg = createExampleDom();

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
