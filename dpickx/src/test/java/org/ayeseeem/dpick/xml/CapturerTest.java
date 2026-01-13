package org.ayeseeem.dpick.xml;

import static org.ayeseeem.dpick.util.dom.DomBuilder.emptyDocument;
import static org.ayeseeem.dpick.xml.StringCapturer.NOOP;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class CapturerTest {

    @Test
    public void testConvertToString() {
        Document doc = emptyDocument();
        doc.appendChild(doc.createElement("SomeRootElement"));
        Node n = doc.getDocumentElement();
        n.setTextContent("The text");

        Capturer<String> subject = new Capturer<>(NOOP);

        subject.capture(n);

        assertThat(subject.value().get(), is("The text"));
    }

    @Test
    public void testConvertToString_EmptyNode() {
        Document doc = emptyDocument();
        doc.appendChild(doc.createElement("SomeRootElement"));
        Node n = doc.getDocumentElement();

        Capturer<String> subject = new Capturer<>(NOOP);

        subject.capture(n);

        assertThat(subject.value().get(), is(""));
    }

    //@Characterization
    @Test(expected = NullPointerException.class)
    public void testConvertToString_RequiresNode() {
        Capturer<String> subject = new Capturer<>(NOOP);

        subject.capture(null);
    }

    @Test
    public void testConvertToInteger() {
        Document doc = emptyDocument();
        doc.appendChild(doc.createElement("SomeRootElement"));
        Node n = doc.getDocumentElement();
        n.setTextContent("111");

        Capturer<Integer> subject = new Capturer<>(Integer::parseInt);

        subject.capture(n);

        assertThat(subject.value().get(), is(111));
    }

    @Test(expected = NumberFormatException.class)
    public void testConvertToInteger_EmptyNode_IsInvalid() {
        Document doc = emptyDocument();
        doc.appendChild(doc.createElement("SomeRootElement"));
        Node n = doc.getDocumentElement();

        Capturer<Integer> subject = new Capturer<>(Integer::parseInt);

        subject.capture(n);
    }

    //@Characterization
    @Test(expected = NullPointerException.class)
    public void testConvertToInteger_RequiresNode() {
        Capturer<Integer> subject = new Capturer<>(Integer::parseInt);

        subject.capture(null);
    }

}
