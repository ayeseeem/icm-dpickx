package org.ayeseeem.dpick.xml;

import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.hamcrest.Matcher;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Static methods for making assertions about XML Nodes found by XPath
 * expressions
 *
 * @author ayeseeem@gmail.com
 *
 */
public class XpathNodeMatchers {

    private final XpathHelper xpathHelper;

    /**
     * Protected constructor - create through static methods, for example in
     * {@link NodeMatchers}.
     *
     * @param xPathExpression
     *            XPath expression
     * @param namespaces
     *            XML namespaces referenced in the XPath expression, or
     *            {@code null}
     * @param xPathExpressionArgs
     *            arguments to parameterize the XPath expression with using the
     *            formatting specifiers defined in
     *            {@link String#format(String, Object...)}
     *
     * @throws XPathExpressionException
     *             if there is a problem parsing the XPath expression
     */
    protected XpathNodeMatchers(String xPathExpression, Map<String, String> namespaces, Object... xPathExpressionArgs)
            throws XPathExpressionException {

        this.xpathHelper = new XpathHelper(xPathExpression, namespaces, xPathExpressionArgs);
    }

    /**
     * Evaluate the XPath on the root node and checks the {@link Node} found
     * with the given Hamcrest {@link Matcher}.
     *
     * @param matcher
     *            a matcher to check the node
     * @return a node matcher that implements the check
     */
    public XmlNodeMatcher node(final Matcher<? super Node> matcher) {
        return rootNode -> {
            throwNotImplementedYet();
            //getChecker(rootNode).assertNode(matcher);
        };
    }

    /**
     * Evaluates the XPath on the root node and checks that node exists
     *
     * @return a node matcher that checks the node exists
     */
    public XmlNodeMatcher exists() {
        return rootNode -> {
            getChecker(rootNode).exists();
        };
    }

    /**
     * Evaluates the XPath on the root node and checks that node does not exist
     *
     * @return a node matcher that checks the node does not exist
     */
    public XmlNodeMatcher doesNotExist() {
        return rootNode -> {
            getChecker(rootNode).doesNotExist();
        };
    }

    /**
     * Evaluates the XPath on the root node and checks the number of nodes found
     *
     * @param expectedCount
     *            the expected node count
     * @return a node matcher that implements the check
     */
    public XmlNodeMatcher nodeCount(final int expectedCount) {
        return rootNode -> {
            getChecker(rootNode).hasNodes(expectedCount);
        };
    }

    /**
     * Evaluates the XPath on the root node and checks the number of nodes found
     * with the given Hamcrest {@link Matcher}.
     *
     * @param matcher
     *            a matcher to check the node count
     * @return a node matcher that implements the check
     */
    public XmlNodeMatcher nodeCount(final Matcher<Integer> matcher) {
        return rootNode -> {
            throwNotImplementedYet();
            //getChecker(rootNode).hasNodes(matcher);
        };
    }

    /**
     * Evaluates the XPath on the root node and checks the node's text content
     * matches the expected value.
     * <p>
     * TODO: ICM 2015-10-12: Need different implementations according to node
     * type? E.g. {@link Node#getNodeValue()} or {@link Node#getTextContent()}
     *
     * @param expectedValue
     *            the expected string value
     * @return a node matcher that implements the check
     */
    public XmlNodeMatcher string(final String expectedValue) {
        return rootNode -> {
            throwNotImplementedYet();
            //getChecker(rootNode).assertString(expectedValue);
        };
    }

    /**
     * Evaluates the XPath on the root node and checks the node's text content
     * with the given Hamcrest {@link Matcher}
     * <p>
     * TODO: ICM 2015-10-12: Need different implementations according to node
     * type? E.g. {@link Node#getNodeValue()} or {@link Node#getTextContent()}
     *
     * @param matcher
     *            a matcher to check the string value
     * @return a node matcher that implements the check
     */
    public XmlNodeMatcher string(final Matcher<? super String> matcher) {
        return rootNode -> {
            throwNotImplementedYet();
            //getChecker(rootNode).assertString(matcher);
        };
    }

    /**
     * Evaluate the XPath on the root node and check the {@link Double} value
     *
     * @param expectedValue
     *            expected value
     * @return a node matcher that implements the check
     */
    public XmlNodeMatcher number(final Double expectedValue) {
        return rootNode -> {
            getChecker(rootNode).assertNumber(expectedValue);
        };
    }

    /**
     * Evaluate the XPath on the root node and check the {@link Double} value
     * with the given Hamcrest {@link Matcher}
     *
     * @param matcher
     *            a matcher to check the value
     * @return a node matcher that implements the check
     */
    public XmlNodeMatcher number(final Matcher<? super Double> matcher) {
        return rootNode -> {
            throwNotImplementedYet();
            //getChecker(rootNode).assertNumber(matcher);
        };
    }

    // TODO: ICM 2015-10-12: create true() and false() instead, or as well as.
    /**
     * Evaluate the XPath on the root node and assert the {@link Boolean} value
     *
     * @param expectedValue
     *            expected value
     * @return a node matcher that implements the check
     */
    public XmlNodeMatcher booleanValue(final Boolean expectedValue) {
        return rootNode -> {
            throwNotImplementedYet();
            //getChecker(rootNode).assertBoolean(expectedValue);
        };
    }

    /**
     * Creates a helper for testing the node(s) selected by the XPath
     *
     * @param rootNode
     *            the root Node on which the XPath is evaluated
     * @return a helper object
     */
    private NodeSelectionChecker getChecker(Node rootNode) {
        final NodeList nodes = xpathHelper.getNodes(rootNode);
        return new NodeSelectionChecker(nodes, "XPath " + xpathHelper.getXPath());
    }

    /**
     * common way to mark the methods that have not been implemented yet
     *
     * @throws UnsupportedOperationException
     *             always
     */
    private void throwNotImplementedYet() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
