package org.ayeseeem.dpick.xml;

import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.ayeseeem.dpick.util.dom.ListOfNode;
import org.hamcrest.Matcher;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Static methods for making assertions about DOM {@link Node}s found by XPath
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
    public NodeMatcher node(Matcher<? super Node> matcher) {
        return rootNode -> throwNotImplementedYet();
        //return rootNode -> getChecker(rootNode).assertNode(matcher);
    }

    /**
     * Evaluates the XPath on the root node and checks that node exists
     *
     * @return a node matcher that checks the node exists
     */
    public NodeMatcher exists() {
        return rootNode -> getChecker(rootNode).exists();
    }

    /**
     * Evaluates the XPath on the root node and checks that node does not exist
     *
     * @return a node matcher that checks the node does not exist
     */
    public NodeMatcher doesNotExist() {
        return rootNode -> getChecker(rootNode).doesNotExist();
    }

    /**
     * Evaluates the XPath on the root node and checks the number of nodes found
     *
     * @param expectedCount
     *            the expected node count
     * @return a node matcher that implements the check
     */
    public NodeMatcher nodeCount(int expectedCount) {
        return rootNode -> getChecker(rootNode).hasNodes(expectedCount);
    }

    /**
     * Evaluates the XPath on the root node and checks the node's text content
     * with the given Hamcrest {@link Matcher}
     *
     * @param matcher
     *            a matcher to check the string value
     * @return a node matcher that implements the check
     */
    public NodeMatcher value(Matcher<String> matcher) {
        return rootNode -> {
            NodeSelectionChecker checker = getChecker(rootNode);
            checker.exists();
            checker.assertMatch(matcher);
        };
    }

    /**
     * Evaluates the XPath on the root node and then processes each matching
     * node with the given {@link NodeHandler}
     *
     * @param nodeHandler
     *            a handler to process a {@link Node}
     * @return a new node handler that applies {@code nodeHander} to each
     *         matched node
     */
    public NodeHandler processEach(NodeHandler nodeHandler) {
        return rootNode -> {
            NodeList nodes = selectNodes(rootNode);
            ListOfNode nodesList = ListOfNode.of(nodes);
            nodesList.forEach(nodeHandler::handle);
        };
    }

    /**
     * Creates a helper for capturing a single, required node specified by the
     * XPath
     *
     * @param capturer
     *            a capturer to capture the node's value
     * @return a new node handler that uses the {@code capturer} to capture the
     *         node's value
     */
    public NodeHandler captureSoleRequired(Capturer capturer) {
        return rootNode -> {
            getChecker(rootNode).exists();
            getChecker(rootNode).hasNodes(1);

            final NodeList nodes = selectNodes(rootNode);
            capturer.capture(nodes.item(0));
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
        final NodeList nodes = selectNodes(rootNode);
        return new NodeSelectionChecker(nodes, "XPath " + xpathHelper.getXPath());
    }

    private NodeList selectNodes(Node rootNode) {
        return xpathHelper.getNodes(rootNode);
    }

    /**
     * Common way to mark the methods that have not been implemented yet
     *
     * @throws UnsupportedOperationException
     *             always
     */
    private void throwNotImplementedYet() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
