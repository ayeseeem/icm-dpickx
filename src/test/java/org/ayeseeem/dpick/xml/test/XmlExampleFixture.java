package org.ayeseeem.dpick.xml.test;

import org.ayeseeem.dpick.util.dom.DomBuilder;
import org.ayeseeem.dpick.util.xml.DomDump;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Example XML for use in integration tests and examples
 */
abstract class XmlExampleFixture {

    protected final Node eg = createExampleDom();

    static {
        Document doc = createExampleDocument();
        DomDump.safeDumpToConsole(doc);
    }

    private static Node createExampleDom() {
        return createExampleDocument().getDocumentElement();
    }

    private static Document createExampleDocument() {
        Document doc = DomBuilder.emptyDocument();

        Element root = doc.createElement("RootElement");
        doc.appendChild(root);

        root.appendChild(doc.createElement("SomethingUnique"));

        root.appendChild(doc.createElement("Repeated"));
        root.appendChild(doc.createElement("Repeated"));

        {
            Element elementWithAttribute = doc.createElement("ElementWithSizeAttribute");
            elementWithAttribute.setAttribute("size", "15");
            root.appendChild(elementWithAttribute);
        }

        {
            Element element = doc.createElement("ContainsSeventeen");
            element.setTextContent("17");
            root.appendChild(element);
        }

        {
            Element element = doc.createElement("ContainsAttributeWithEighteen");
            element.setTextContent("blah blah");
            element.setAttribute("attrOf18", "18");
            root.appendChild(element);
        }

        {
            Element element = doc.createElement("Duplicate");
            element.setTextContent("123");
            root.appendChild(element);
        }
        {
            Element element = doc.createElement("Duplicate");
            element.setTextContent("123");
            root.appendChild(element);
        }

        {
            Element element = doc.createElement("DuplicateEleDiffContent");
            element.setTextContent("123");
            root.appendChild(element);
        }
        {
            Element element = doc.createElement("DuplicateEleDiffContent");
            element.setTextContent("456");
            root.appendChild(element);
        }

        {
            Element element = doc.createElement("AlwaysTrue");
            element.setTextContent("true");
            root.appendChild(element);
        }

        return doc;
    }

}
