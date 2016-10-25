package org.ayeseeem.dpick.util.xml;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomHelperTest {

    @Test
    public void testGetNodes_ExampleFromWholeDom() throws Exception {
        test = new DomHelper(createDoc("<stuff>blah, <sub>more</sub> blah</stuff>"));

        NodeList nodes = test.getNodes("/stuff");

        assertEquals(1, nodes.getLength());
        Node node0 = nodes.item(0);
        assertEquals(Node.ELEMENT_NODE, node0.getNodeType());
        assertEquals("stuff", node0.getNodeName());
        assertEquals("blah, more blah", node0.getTextContent());
    }

    @Test
    public void testGetNodes_FromSubNode() throws Exception {
        test = new DomHelper(createDoc("<stuff>blah, <sub>more<inner>inside</inner></sub> blah</stuff>"));
        Node subNode = test.getNodes("/stuff/sub").item(0);
        assertEquals("sub", subNode.getNodeName());

        // NOTE the use of the "./" to indicate relative to node/current context
        NodeList nodes = test.getNodes(subNode, "./inner");

        assertEquals(1, nodes.getLength());
        Node node0 = nodes.item(0);
        assertEquals(Node.ELEMENT_NODE, node0.getNodeType());
        assertEquals("inner", node0.getNodeName());
        assertEquals("inside", node0.getTextContent());
    }

    @Test
    public void testGetNodes_FromSubNode_CanStillAccessWholeDocument() throws Exception {
        test = new DomHelper(createDoc("<stuff>blah, <sub>more<inner>inside</inner></sub> blah</stuff>"));
        Node subNode = test.getNodes("/stuff/sub").item(0);
        assertEquals("sub", subNode.getNodeName());

        // NOTE the use of the "/" to indicate relative to document root
        {
            // finds top-level element above current node
            NodeList nodes = test.getNodes(subNode, "/stuff");
            assertEquals(1, nodes.getLength());
        }
        {
            // no inner elements founds: not at top of tree
            NodeList nodes = test.getNodes(subNode, "/inner");
            assertEquals(0, nodes.getLength());
        }
    }

    @Test
    public void testSetNsContext() throws Exception {
        test = new DomHelper(createDoc("<egNs:stuff xmlns:egNs=\"http://example.com/eg/ns\">blah, blah</egNs:stuff>"));
        test.setNsContext(nsContext);

        NodeList nodes = test.getNodes("/egNs:stuff");

        assertEquals(1, nodes.getLength());
        Node node0 = nodes.item(0);
        assertEquals(Node.ELEMENT_NODE, node0.getNodeType());
        assertEquals("egNs:stuff", node0.getNodeName());
        assertEquals("blah, blah", node0.getTextContent());
    }

    @Test
    public void testSetNsContext_DefaultNamespaceSpecified() throws Exception {
        test = new DomHelper(createDoc("<stuff xmlns=\"http://example.com/eg/ns\">blah, blah</stuff>"));
        test.setNsContext(nsContext);

        NodeList nodes = test.getNodes("/egNs:stuff");

        assertEquals(1, nodes.getLength());
        Node node0 = nodes.item(0);
        assertEquals(Node.ELEMENT_NODE, node0.getNodeType());
        assertEquals("stuff", node0.getNodeName());
        assertEquals("blah, blah", node0.getTextContent());
    }

    private DomHelper test;

    private NamespaceContext nsContext = new NamespaceContext() {
        @Override
        public String getNamespaceURI(String prefix) {
            return prefix.equals("egNs") ? "http://example.com/eg/ns" : null;
        }
        @Override
        public Iterator<?> getPrefixes(String val) {
            return null;
        }
        @Override
        public String getPrefix(String uri) {
            return null;
        }
    };

    private Document createDoc(String xmlString) throws SAXException {
        return new XmlUnmarshaller(true).toDocument(xmlString);
    }

}
