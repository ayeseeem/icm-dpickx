package org.ayeseeem.dpick.util.xml;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DomDumpTest {

    @Test
    public void testSafeDump_XmlDocument() {
        DomDump.safeDump(doc, sink);
        assertThat(dumpedString(), startsWith("<?xml"));
        assertThat(dumpedString(), startsWith("<?xml version=\"1.0\""));
    }

    @Test
    public void testSafeDump_XmlDocument_Utf8Encoding() {
        DomDump.safeDump(doc, sink);
        assertThat(dumpedString(), containsString("encoding=\"UTF-8\""));
    }

    @Test
    public void testSafeDump_EmptyDocument() {
        DomDump.safeDump(doc, sink);
        assertThat(dumpedString(), startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"));
    }

    @Test
    public void testSafeDump_XmlDocument_WithRootElement() {
        doc.appendChild(doc.createElement("RootElement"));

        DomDump.safeDump(doc, sink);
        assertThat(dumpedString(), containsString("<RootElement"));
    }

    @Test
    public void testSafeDump_UsesSystemLineSeparator() {
        doc.appendChild(doc.createElement("RootElement"));

        DomDump.safeDump(doc, sink);
        assertThat(dumpedString(), containsString(">" + System.lineSeparator() + "<RootElement"));
    }

    @Test
    public void testSafeDump_XmlDocument_SubElements() {
        Element root = doc.createElement("RootElement");
        doc.appendChild(root);

        root.appendChild(doc.createElement("SomethingUnique"));

        DomDump.safeDump(doc, sink);
        DomDump.safeDumpToConsole(doc);
        assertThat(dumpedString(), containsString("<SomethingUnique"));
    }

    @Test
    public void testSafeDump_XmlDocument_Indents_2Spaces() {
        Element root = doc.createElement("RootElement");
        doc.appendChild(root);

        root.appendChild(doc.createElement("SomethingUnique"));

        DomDump.safeDump(doc, sink);
        DomDump.safeDumpToConsole(doc);
        assertThat(dumpedString(), containsString(" " + " " + "<SomethingUnique"));
        assertThat(dumpedString(), not(containsString(" " + " " + " " + "<SomethingUnique")));
    }

    private Document doc;

    private final ByteArrayOutputStream sink = new ByteArrayOutputStream();

    private String dumpedString() {
        return new String(sink.toByteArray());
    }

    @Before
    public void setUp() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new Error("XML exception - translated to error", e);
        }
        doc = builder.newDocument();
    }

}
