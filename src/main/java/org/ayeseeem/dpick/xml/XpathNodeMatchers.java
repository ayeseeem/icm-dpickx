package org.ayeseeem.dpick.xml;

import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.ayeseeem.dpick.util.dom.ListOfNode;
import org.hamcrest.Matcher;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: ICM 2019-03-31: Separate out handlers into NodeHandlers?
// TODO: ICM 2019-03-31: Rename as XpathNodeActions?? as it contains matchers _and_ handlers?
// TODO: ICM 2019-03-29: Rename, re-work with (renamed?) NodeMatchers
// - extract an interface - NodeChecks? that could apply also to JSONPath?
// - separate the XPath parts into an xpath sub-package

/**
 * Methods for making assertions about DOM {@link Node}s found by XPath
 * expressions.
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
     * Evaluates the XPath on the root node and checks the {@link Node} found
     * with the given Hamcrest {@link Matcher}.
     *
     * @param matcher
     *            a matcher to check the node
     * @return a node matcher that implements the check
     */
    public NodeMatcher node(Matcher<? super Node> matcher) {
        return rootNode -> throwNotImplementedYet();
        //return matcherFromHandler(selection -> assertNode(matcher));
    }

    /**
     * Evaluates the XPath on the root node and checks that node exists.
     *
     * @return a node matcher that checks the node exists
     */
    public NodeMatcher exists() {
        return matcherFromHandler(NodeSelectionChecker::exists);
    }

    /**
     * Evaluates the XPath on the root node and checks that node does not exist.
     *
     * @return a node matcher that checks the node does not exist
     */
    public NodeMatcher doesNotExist() {
        return matcherFromHandler(NodeSelectionChecker::doesNotExist);
    }

    /**
     * Evaluates the XPath on the root node and checks the number of nodes found.
     *
     * @param expectedCount
     *            the expected node count
     * @return a node matcher that implements the check
     */
    public NodeMatcher nodeCount(int expectedCount) {
        return matcherFromHandler(selection -> selection.hasNodes(expectedCount));
    }

    /**
     * Evaluates the XPath on the root node and checks the node's text content
     * with the given Hamcrest {@link Matcher}.
     *
     * @param matcher
     *            a matcher to check the string value
     * @return a node matcher that implements the check
     */
    public NodeMatcher value(Matcher<String> matcher) {
        return matcherFromHandler(selection -> {
            selection.exists();
            selection.assertMatch(matcher);
        });
    }

    /**
     * Evaluates the XPath on the root node and then processes each matching
     * node with the given {@link NodeHandler}.
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
     * XPath.
     *
     * @param capturer
     *            a capturer to capture the node's value
     * @return a new node handler that uses the {@code capturer} to capture the
     *         node's value
     */
    public NodeHandler captureSoleRequired(Capturer capturer) {
        return rootNode -> {
            matcherFromHandler(selection -> {
                selection.exists();
                selection.hasNodes(1);
            }).match(rootNode);

            final NodeList nodes = selectNodes(rootNode);
            capturer.capture(nodes.item(0));
        };
    }

    private NodeList selectCheckedNodes(Node rootNode, Checks checks) {
        final NodeList nodes = selectNodes(rootNode);
        NodeSelectionChecker checker = new NodeSelectionChecker(nodes, "XPath " + xpathHelper.getXPath());
        checks.checkWith(checker);
        return nodes;
    }

    // TODO: ICM 2019-03-31: move to NodeSelectionChecker?
    @FunctionalInterface
    interface Checks {
        void checkWith(NodeSelectionChecker checker);
    }

    // TODO: ICM 2019-03-31: Why the name? it doesn't do handle()?
    private NodeMatcher matcherFromHandler(Checks checks) {
        return rootNode -> selectCheckedNodes(rootNode, checks);
    }

    private NodeList selectNodes(Node rootNode) {
        return xpathHelper.getNodes(rootNode);
    }

    /**
     * Common way to mark the methods that have not been implemented yet.
     *
     * @throws UnsupportedOperationException
     *             always
     */
    private void throwNotImplementedYet() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
