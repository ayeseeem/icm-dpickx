package org.ayeseeem.dpick.xml;

import java.util.Optional;

import org.w3c.dom.Node;

/**
 * Class for checking XML (or HTML) documents. Can check anything that can be
 * presented as a {@link Node}.
 */
public class XmlDocumentChecker {

    private final Node rootNode;

    public XmlDocumentChecker(Node rootNode) {
        this.rootNode = rootNode;
    }

    public String captureSoleRequired(XpathNodeMatchers xpathNodeMatchers) {
        final Capturer capturer = new Capturer();

        check(rootNode).andDo(xpathNodeMatchers.captureSoleRequired(capturer));

        final Optional<String> value = capturer.value();
        assert value.isPresent();
        return value.get();
    }

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
