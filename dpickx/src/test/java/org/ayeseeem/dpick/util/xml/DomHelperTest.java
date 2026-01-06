package org.ayeseeem.dpick.util.xml;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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

        assertThat(nodes.getLength(), is(1));
        Node node0 = nodes.item(0);
        assertThat(node0.getNodeType(), is(Node.ELEMENT_NODE));
        assertThat(node0.getNodeName(), is("stuff"));
        assertThat(node0.getTextContent(), is("blah, more blah"));
    }

    @Test
    public void testGetNodes_FromSubNode() throws Exception {
        test = new DomHelper(createDoc("<stuff>blah, <sub>more<inner>inside</inner></sub> blah</stuff>"));
        Node subNode = test.getNodes("/stuff/sub").item(0);
        assertThat(subNode.getNodeName(), is("sub"));

        // NOTE the use of the "./" to indicate relative to node/current context
        NodeList nodes = test.getNodes(subNode, "./inner");

        assertThat(nodes.getLength(), is(1));
        Node node0 = nodes.item(0);
        assertThat(node0.getNodeType(), is(Node.ELEMENT_NODE));
        assertThat(node0.getNodeName(), is("inner"));
        assertThat(node0.getTextContent(), is("inside"));
    }

    @Test
    public void testGetNodes_FromSubNode_CanStillAccessWholeDocument() throws Exception {
        test = new DomHelper(createDoc("<stuff>blah, <sub>more<inner>inside</inner></sub> blah</stuff>"));
        Node subNode = test.getNodes("/stuff/sub").item(0);
        assertThat(subNode.getNodeName(), is("sub"));

        // NOTE the use of the "/" to indicate relative to document root
        {
            // finds top-level element above current node
            NodeList nodes = test.getNodes(subNode, "/stuff");
            assertThat(nodes.getLength(), is(1));
        }
        {
            // no inner elements found: not at top of tree
            NodeList nodes = test.getNodes(subNode, "/inner");
            assertThat(nodes.getLength(), is(0));
        }
    }

    @Test
    public void testSetNsContext() throws Exception {
        test = new DomHelper(createDoc("<egNs:stuff xmlns:egNs=\"http://example.com/eg/ns\">blah, blah</egNs:stuff>"));
        test.setNsContext(nsContext);

        NodeList nodes = test.getNodes("/egNs:stuff");

        assertThat(nodes.getLength(), is(1));
        Node node0 = nodes.item(0);
        assertThat(node0.getNodeType(), is(Node.ELEMENT_NODE));
        assertThat(node0.getNodeName(), is("egNs:stuff"));
        assertThat(node0.getTextContent(), is("blah, blah"));
    }

    @Test
    public void testSetNsContext_DefaultNamespaceSpecified() throws Exception {
        test = new DomHelper(createDoc("<stuff xmlns=\"http://example.com/eg/ns\">blah, blah</stuff>"));
        test.setNsContext(nsContext);

        NodeList nodes = test.getNodes("/egNs:stuff");

        assertThat(nodes.getLength(), is(1));
        Node node0 = nodes.item(0);
        assertThat(node0.getNodeType(), is(Node.ELEMENT_NODE));
        assertThat(node0.getNodeName(), is("stuff"));
        assertThat(node0.getTextContent(), is("blah, blah"));
    }

    private DomHelper test;

    private NamespaceContext nsContext = new NamespaceContext() {
        @Override
        public String getNamespaceURI(String prefix) {
            return prefix.equals("egNs") ? "http://example.com/eg/ns" : null;
        }

        @Override
        public Iterator<String> getPrefixes(String val) {
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
