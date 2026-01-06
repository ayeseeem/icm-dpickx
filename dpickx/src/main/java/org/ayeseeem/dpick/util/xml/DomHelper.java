package org.ayeseeem.dpick.util.xml;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomHelper {

    protected final Node rootNode;

    private NamespaceContext nsContext;

    public DomHelper (Node rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * Sets a namespace context for so that the XPaths used by
     * {@link DomHelper#getNodes(String)} will work with XML namespaces
     *
     * @param nsContext
     *            context to use
     */
    public void setNsContext(NamespaceContext nsContext) {
        this.nsContext = nsContext;
    }

    /**
     * Gets nodes from the whole DOM of the rootNode of this helper.
     * <p>
     * To select relative to just part of the DOM, see
     * {@link #getNodes(Node, String)}.
     *
     * @param xPathExpression
     *            expression to select Nodes
     * @return all matching nodes from the whole DOM
     * @throws XPathExpressionException
     *             if there is a problem compiling or evaluating the XPath
     */
    protected NodeList getNodes(String xPathExpression) throws XPathExpressionException {
        return getNodes(rootNode, xPathExpression);
    }

    /**
     * Gets nodes relative to the specified {@code rootNode}. Note that absolute
     * XPaths - for example, {@code /topLevel}, still work with respect to the whole
     * DOM. To select relative to the Node, use relative XPaths such as
     * {@code ./here} or {@code .//any}
     * <p>
     * To select from the whole DOM, also see {@link #getNodes(String)}.
     *
     * @param rootNode
     *            node to select relative to
     * @param xPathExpression
     *            expression to select Nodes
     * @return all matching nodes
     * @throws XPathExpressionException
     *             if there is a problem compiling or evaluating the XPath
     */
    protected NodeList getNodes(Node rootNode, String xPathExpression) throws XPathExpressionException {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        if (nsContext != null) {
            xpath.setNamespaceContext(nsContext);
        }
        final XPathExpression expr = xpath.compile(xPathExpression);

        NodeList nodes = (NodeList) expr.evaluate(rootNode, XPathConstants.NODESET);
        return nodes;
    }

}
