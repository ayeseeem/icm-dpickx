package org.ayeseeem.dpick.xml;

import static org.ayeseeem.dpick.util.dom.DomBuilder.emptyDocument;
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

        Capturer subject = new Capturer();

        subject.capture(n);

        assertThat(subject.value().get(), is("The text"));
    }

    @Test
    public void testConvertToString_EmptyNode() {
        Document doc = emptyDocument();
        doc.appendChild(doc.createElement("SomeRootElement"));
        Node n = doc.getDocumentElement();


        Capturer subject = new Capturer();

        subject.capture(n);

        assertThat(subject.value().get(), is(""));
    }

    //@Characterization
    @Test(expected = NullPointerException.class)
    public void testConvertToString_RequiresNode() {
        Capturer subject = new Capturer();

        subject.capture(null);
    }

}
