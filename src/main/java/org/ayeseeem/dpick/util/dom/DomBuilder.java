package org.ayeseeem.dpick.util.dom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class DomBuilder {

    public static Document emptyDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new IllegalStateException("DOM Builder exception - translated to error", e);
        }
        Document emptyDocument = builder.newDocument();
        assert emptyDocument.getChildNodes().getLength() == 0;
        return emptyDocument;
    }

    /**
     * Creates an empty node list.
     *
     * @return an empty {@link NodeList}
     */
    public static NodeList emptyNodeList() {
        NodeList empty = emptyDocument().getChildNodes();
        assert empty.getLength() == 0;
        return empty;
    }

}
