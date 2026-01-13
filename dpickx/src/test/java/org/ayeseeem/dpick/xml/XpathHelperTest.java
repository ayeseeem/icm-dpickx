package org.ayeseeem.dpick.xml;

import static java.util.Collections.emptyMap;
import static org.ayeseeem.dpick.util.dom.DomBuilder.emptyDocument;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XpathHelperTest {

    //@Characterization
    @Test(expected = NullPointerException.class)
    public void testGetNodes_RootNodeCannotBeNull() throws XPathExpressionException {
        XpathHelper subject = new XpathHelper("somePath", emptyMap());

        subject.getNodes(null);
    }

    @Test
    public void testGetNodes_ReturnsEmptySetIfNothingFound() throws XPathExpressionException {
        Document doc = emptyDocument();
        doc.appendChild(doc.createElement("SomeRootElement"));
        Node n = doc.getDocumentElement();

        XpathHelper subject = new XpathHelper("NonExistentNode", emptyMap());

        NodeList result = subject.getNodes(n);
        assertThat(result, is(not(nullValue())));
        assertThat(result.getLength(), is(0));
    }

    @Test
    public void testGetNodes_FiltersWithinTheNode_NotDocRoot() throws XPathExpressionException {
        Document doc = emptyDocument();
        Element root = doc.createElement("SomeRootElement");
        doc.appendChild(root);

        {
            Element element = doc.createElement("ele");
            element.setTextContent("111");
            root.appendChild(element);
        }

        final Node wanted;
        {
            Element element = doc.createElement("wanted");
            wanted = root.appendChild(element);
        }

        {
            Element element = doc.createElement("ele");
            element.setTextContent("222");
            wanted.appendChild(element);
        }

        {
            Element element = doc.createElement("ele");
            element.setTextContent("333");
            wanted.appendChild(element);
        }

        // Find relative to passed in node
        {
            XpathHelper subject = new XpathHelper("./ele", emptyMap());

            NodeList result = subject.getNodes(wanted);
            assertThat(result.item(0).getTextContent(), is("222"));
            assertThat(result.item(1).getTextContent(), is("333"));
            assertThat(result.getLength(), is(2));
        }

        // Find all
        {
            XpathHelper subject = new XpathHelper("//ele", emptyMap());

            NodeList result = subject.getNodes(wanted);
            assertThat(result.item(0).getTextContent(), is("111"));
            assertThat(result.item(1).getTextContent(), is("222"));
            assertThat(result.item(2).getTextContent(), is("333"));
            assertThat(result.getLength(), is(3));
        }
    }

    //@Characterization
    @Test
    public void testGetNodes_ExampleOfContextlessXpath_Number() throws XPathExpressionException {
        Document doc = emptyDocument();
        doc.appendChild(doc.createElement("SomeRootElement"));
        Node n = doc.getDocumentElement();

        XpathHelper subject = new XpathHelper("1+1", emptyMap());

        NodeList result = subject.getNodes(n);
        assertThat(result, is(not(nullValue())));
        assertThat(result.getLength(), is(0));
    }

    //@Characterization
    @Test
    public void testGetNodes_ExampleOfContextlessXpath_String() throws XPathExpressionException {
        Document doc = emptyDocument();
        doc.appendChild(doc.createElement("SomeRootElement"));
        Node n = doc.getDocumentElement();

        XpathHelper subject = new XpathHelper("concat('ABC ','DEF')", emptyMap());

        NodeList result = subject.getNodes(n);
        assertThat(result, is(not(nullValue())));
        assertThat(result.getLength(), is(0));
    }

}
