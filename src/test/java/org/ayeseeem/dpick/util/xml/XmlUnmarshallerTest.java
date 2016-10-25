package org.ayeseeem.dpick.util.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XmlUnmarshallerTest {

    @Test
    public void testDefaultsToNamespaceNotAware(){
        test = new XmlUnmarshaller();
        assertFalse(test.isNamespaceAware());
    }

    @Test
    public void testToDocument_ValidXml() throws Exception {
        Document document = test.toDocument("<stuff>this is simple XML</stuff>");

        Node root = document.getFirstChild();
        assertEquals("stuff", root.getNodeName());
        assertEquals("this is simple XML", root.getFirstChild().getNodeValue());
    }

    @Test
    public void testToDocument_InvalidXml() throws Exception {
        thrown.expect(SAXException.class);
        test.toDocument("<stuff>this is <broken> XML</stuff>");
    }

    @Test
    public void testToDocument_NonXml() throws Exception {
        thrown.expect(SAXException.class);
        test.toDocument("This is NOT XML");
    }

    @Test
    public void testToDocument_HandlesNamespacing_WhenNotNamespaceAware() throws Exception {
        test = new XmlUnmarshaller(false);
        Document document = test.toDocument("<someNameSpace:stuff></someNameSpace:stuff>");
        assertEquals("someNameSpace:stuff", document.getDocumentElement().getNodeName());
    }

    @Test
    public void testToDocument_HandlesNamespacing_WhenNamespaceAware() throws Exception {
        test = new XmlUnmarshaller(true);
        Document document = test.toDocument("<someNameSpace:stuff xmlns:someNameSpace=\"http://example.com/ns\"></someNameSpace:stuff>");
        assertEquals("someNameSpace:stuff", document.getDocumentElement().getNodeName());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private XmlUnmarshaller test = new XmlUnmarshaller();

}
