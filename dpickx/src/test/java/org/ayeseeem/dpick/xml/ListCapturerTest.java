package org.ayeseeem.dpick.xml;

import static java.util.Arrays.asList;
import static org.ayeseeem.dpick.util.dom.DomBuilder.emptyDocument;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ListCapturerTest {

    @Test
    public void testInitialValueIsEmpty() {
        ListCapturer subject = new ListCapturer();
        assertThat(subject.value(), is(empty()));
    }

    @Test
    public void testCaptureNodes() {
        Document doc = emptyDocument();
        Element root = doc.createElement("SomeRootElement");
        doc.appendChild(root);

        final Node node1;
        {
            Element element = doc.createElement("Node1");
            element.setTextContent("111");
            node1 = root.appendChild(element);
        }

        final Node node2;
        {
            Element element = doc.createElement("Node2");
            element.setTextContent("222");
            node2 = root.appendChild(element);
        }

        final Node node3;
        {
            Element element = doc.createElement("Node3");
            element.setTextContent("333");
            node3 = root.appendChild(element);
        }

        ListCapturer subject = new ListCapturer();

        subject.capture(asList(node1, node2, node3));

        assertThat(subject.value(), contains("111", "222", "333"));
    }

    @Test
    public void testCaptureNodes_EmptyList() {
        ListCapturer subject = new ListCapturer();
        assertThat(subject.value(), is(empty()));

        subject.capture(new ArrayList<>());

        assertThat(subject.value(), is(empty()));
    }

    //@Characterization
    @Test(expected = NullPointerException.class)
    public void testCaptureNodes_RequiresList() {
        ListCapturer subject = new ListCapturer();

        subject.capture((List<Node>) null);
    }

}
