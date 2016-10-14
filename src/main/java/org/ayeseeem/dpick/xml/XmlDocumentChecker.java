package org.ayeseeem.dpick.xml;

import org.w3c.dom.Node;

/**
 * Class for checking XML documents.
 *
 * @author ayeseeem@gmail.com
 *
 */
public class XmlDocumentChecker {
    public static XmlNodeActions check(Node rootNode) {
        return new XmlNodeActions() {
            @Override
            public XmlNodeActions andExpect(XmlNodeMatcher matcher) {
                matcher.match(rootNode);
                return this;
            }

            @Override
            public XmlNodeActions andDo(XmlNodeHandler handler) {
                handler.handle(rootNode);
                return this;
            }
        };
    }

}
