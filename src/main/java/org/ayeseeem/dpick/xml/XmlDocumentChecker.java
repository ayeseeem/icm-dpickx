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

    /**
     * Captures the value of the single, optional node specified by the XPath
     * matchers. The current definition is that there can be one or zero values; it
     * does not have to be there - it's optional - but if it's there, it must be the
     * only one: there cannot be more than one.
     * 
     * @param xpathNodeMatchers the XPath to capture from
     * @return an {@code Optional} for the possible value 
     */
    public Optional<String> captureSoleOptional(XpathNodeMatchers xpathNodeMatchers) {
        final Capturer capturer = new Capturer();

        XmlDocumentChecker.check(rootNode)
                .andDo(xpathNodeMatchers.captureSoleOptional(capturer));

        return capturer.value();
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
