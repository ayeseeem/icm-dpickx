package org.ayeseeem.dpick.util.xml;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XmlUnmarshallerTest {

    @Test
    public void testDefaultsToNamespaceNotAware(){
        test = new XmlUnmarshaller();
        assertThat(test.isNamespaceAware(), is(false));
    }

    @Test
    public void testToDocument_ValidXml() throws Exception {
        Document document = test.toDocument("<stuff>this is simple XML</stuff>");

        Node root = document.getFirstChild();
        assertThat(root.getNodeName(), is("stuff"));
        assertThat(root.getFirstChild().getNodeValue(), is("this is simple XML"));
    }

    @Test
    public void testToDocument_InvalidXml() {
        assertThrows(SAXException.class, () -> {
            test.toDocument("<stuff>this is <broken> XML</stuff>");
        });
    }

    @Test
    public void testToDocument_NonXml() {
        assertThrows(SAXException.class, () -> {
            test.toDocument("This is NOT XML");
        });
    }

    @Test
    public void testToDocument_HandlesNamespacing_WhenNotNamespaceAware() throws Exception {
        test = new XmlUnmarshaller(false);
        Document document = test.toDocument("<someNameSpace:stuff></someNameSpace:stuff>");
        assertThat(document.getDocumentElement().getNodeName(), is("someNameSpace:stuff"));
    }

    @Test
    public void testToDocument_HandlesNamespacing_WhenNamespaceAware() throws Exception {
        test = new XmlUnmarshaller(true);
        Document document = test.toDocument("<someNameSpace:stuff xmlns:someNameSpace=\"http://example.com/ns\"></someNameSpace:stuff>");
        assertThat(document.getDocumentElement().getNodeName(), is("someNameSpace:stuff"));
    }

    private XmlUnmarshaller test = new XmlUnmarshaller();

}
