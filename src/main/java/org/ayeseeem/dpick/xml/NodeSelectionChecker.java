package org.ayeseeem.dpick.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class to help make assertions about a selection of XML Nodes, for example
 * when selected by XPath expressions
 *
 * @author ayeseeem@gmail.com
 *
 */
public class NodeSelectionChecker {

    private final NodeList nodes;
    private final String selectionExpression;

    /**
     * Creates a helper for analysing the resultant {@link NodeList}
     *
     * @param nodes
     *            the node list
     * @param selectionExpression
     *            string describing how the node list was selected, for use in
     *            debug and error messages
     */
    public NodeSelectionChecker(NodeList nodes, String selectionExpression) {
        this.nodes = nodes;
        this.selectionExpression = selectionExpression;
    }

    public void exists() {
        assertTrue(nodes.getLength() > 0, this.selectionExpression + " does not exist");
    }

    public void doesNotExist() {
        // TODO: ICM 2016-10-14: Clarify message: "exists (unexpectedly)"
        assertTrue(nodes.getLength() == 0, this.selectionExpression + " exists");
    }

    public void hasNodes(int expectedCount) {
        int length = nodes.getLength();
        assertTrue(length == expectedCount, "Expected " + expectedCount + " nodes for " + this.selectionExpression + ", not " + length);
    }

    public void assertNumber(Double expectedValue) {
        final List<String> valueStrings = getValueStrings(nodes);
        valueStrings.forEach(valueString -> {
            final double value = Double.parseDouble(valueString);
            assertTrue(value == expectedValue, "Expected a number " + expectedValue + " for " + this.selectionExpression + ", not " + value);
        });
    }

    // HACK: ICM 2016-10-15: In progress - need better processing of text content
    // TODO: ICM 2016-10-15: Does this support attribute values yet?
    public static List<String> getValueStrings(NodeList nodes) {
        final List<String> valueStrings = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            final Node node = nodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String valueString = element.getTextContent();
                valueStrings.add(valueString);
            }
        }
        return valueStrings;
    }

    /**
     * Asserts that the given condition is {@code true}, and fails if it is not
     *
     * @param condition
     *            the condition that is expected to be true
     * @param message
     *            the message describing the error case. If a clear error case
     *            message cannot be devised, then just describe that the
     *            expected case was not met.
     * @throws AssertionError
     *             if the condition is {@code false}
     */
    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            fail(message);
        }
    }

    /**
     * Handles a failure
     *
     * @param message
     *            the reason for the failure
     * @throws AssertionError
     *             always, with the given message
     */
    public static void fail(String message) {
        // TODO: ICM 2016-10-14: Throw a library-specific Error - perhaps extension of AssertionError
        throw new AssertionError(message);
    }

}
