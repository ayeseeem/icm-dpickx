package org.ayeseeem.dpick.xml.test;

import org.ayeseeem.dpick.util.dom.DomBuilder;
import org.ayeseeem.dpick.util.xml.DomDump;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Example XML for use in integration tests and examples.
 */
class XmlExampleFixture {

    static {
        Document doc = createExampleDocument();
        DomDump.safeDumpToConsole(doc);
    }

    static Node createExampleDom() {
        return createExampleDocument().getDocumentElement();
    }

    static Document createExampleDocument() {
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
            element.setTextContent("111");
            root.appendChild(element);
        }
        {
            Element element = doc.createElement("DuplicateEleDiffContent");
            element.setTextContent("222");
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
