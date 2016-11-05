package org.ayeseeem.dpick.xml;

import org.w3c.dom.Node;

/**
 * Class for checking XML (or HTML) documents. Can check anything that can be
 * presented as a {@link Node}.
 *
 * @author ayeseeem@gmail.com
 *
 */
public class XmlDocumentChecker {
    public static NodeActions check(Node rootNode) {
        return new NodeActions() {
            @Override
            public NodeActions andExpect(NodeMatcher matcher) {
                matcher.match(rootNode);
                return this;
            }

            @Override
            public NodeActions andDo(NodeHandler handler) {
                handler.handle(rootNode);
                return this;
            }
        };
    }

}
