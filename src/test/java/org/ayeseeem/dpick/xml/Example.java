package org.ayeseeem.dpick.xml;

import static org.ayeseeem.dpick.xml.NodeMatchers.xpath;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;

public class Example {

    public void exampleProcessNode(Node node) {
        try {
            XmlDocumentChecker.check(node).andExpect(xpath("/Request/Attributes").exists())
                    .andExpect(xpath("//Attribute").exists())
                    .andExpect(xpath("//Attribute").nodeCount(2))
                    .andExpect(xpath("/NonExistent").doesNotExist())
                    .andExpect(xpath("/Attribute").doesNotExist()); // not a top-level element
        } catch (XPathExpressionException | AssertionError e) {
            throw new RuntimeException("dpickx: problem with XPath", e);
        }
    }
}
