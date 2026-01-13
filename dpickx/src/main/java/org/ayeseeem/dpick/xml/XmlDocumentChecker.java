package org.ayeseeem.dpick.xml;

import static org.ayeseeem.dpick.xml.StringCapturer.NOOP;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.w3c.dom.Node;

/**
 * Class for checking XML (or HTML) documents. Can check anything that can be
 * presented as a {@link Node}.
 */
public class XmlDocumentChecker {

    private final Node rootNode;

    public XmlDocumentChecker(Node rootNode) {
        Objects.requireNonNull(rootNode);
        this.rootNode = rootNode;
    }

    /**
     * Captures the values of all the nodes specified by the XPath
     * matchers. If no nodes are matched, returns an empty list.
     *
     * @param xpathNodeMatchers
     *            the XPath to capture from
     * @return the values of the nodes
     */
    public List<String> captureAll(XpathNodeMatchers xpathNodeMatchers) {
        final ListCapturer capturer = new ListCapturer();

        check(rootNode).andDo(xpathNodeMatchers.captureAll(capturer));

        return capturer.value();
    }

    /**
     * Captures the text value of the single, required node specified by the XPath
     * matchers. The node must be present, and it must be the only one: there
     * cannot be more than one.
     *
     * @param xpathNodeMatchers
     *            the XPath to capture from
     * @return the value of the node
     */
    public String captureSoleRequired(XpathNodeMatchers xpathNodeMatchers) {
        return captureSoleRequired(xpathNodeMatchers, NOOP);
    }

    /**
     * Captures the value of the single, required node specified by the XPath
     * matchers. The node must be present, and it must be the only one: there cannot
     * be more than one.
     *
     * @param xpathNodeMatchers
     *            the XPath to capture from
     * @param converter
     *            used to convert the text value of the node to the specified type T
     * @return the value of the node
     */
    public <T> T captureSoleRequired(XpathNodeMatchers xpathNodeMatchers, Function<String, T> converter) {
        final Capturer<T> capturer = new Capturer<>(converter);

        check(rootNode).andDo(xpathNodeMatchers.captureSoleRequired(capturer));

        final Optional<T> value = capturer.value();
        assert value.isPresent();
        return value.get();
    }

    /**
     * Captures the text value of the single, optional node specified by the XPath
     * matchers. The current definition is that there can be one or zero values; it
     * does not have to be there - it's optional - but if it's there, it must be the
     * only one: there cannot be more than one.
     *
     * @param xpathNodeMatchers
     *            the XPath to capture from
     * @return an {@code Optional} for the possible value
     */
    public Optional<String> captureSoleOptional(XpathNodeMatchers xpathNodeMatchers) {
        return captureSoleOptional(xpathNodeMatchers, NOOP);
    }

    /**
     * Captures the value of the single, optional node specified by the XPath
     * matchers. The current definition is that there can be one or zero values; it
     * does not have to be there - it's optional - but if it's there, it must be the
     * only one: there cannot be more than one.
     *
     * @param xpathNodeMatchers
     *            the XPath to capture from
     * @param converter
     *            used to convert the text value of the node to the specified type T
     * @return an {@code Optional} for the possible value
     */
    public <T> Optional<T> captureSoleOptional(XpathNodeMatchers xpathNodeMatchers, Function<String, T> converter) {
        final Capturer<T> capturer = new Capturer<>(converter);

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
