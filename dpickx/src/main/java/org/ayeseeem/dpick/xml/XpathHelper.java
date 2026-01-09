package org.ayeseeem.dpick.xml;

import static org.ayeseeem.dpick.util.dom.DomBuilder.emptyNodeList;

import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class to help select {@link Node}s with XPath expressions.
 */
public class XpathHelper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String xPath;
    private final XPathExpression expression;

    public XpathHelper(String xPath, @SuppressWarnings("unused") Map<String, String> namespaces, Object... args) throws XPathExpressionException {
        this.xPath = String.format(xPath, args);
        expression = createXpathExpression(this.xPath);
    }

    /**
     * Gets the full XPath representation, with any of the supplied args
     * applied.
     *
     * @return the XPath
     */
    public String getXPath() {
        return xPath;
    }

    /**
     * Selects node(s) by applying the XPath. For help making assertions about
     * the result, see {@link XpathNodeMatchers}.
     *
     * @param rootNode
     *            the root Node on which the XPath is evaluated
     * @return the result of the XPath selection
     */
    public NodeList getNodes(Node rootNode) {
        try {
            final NodeList nodes = (NodeList) expression.evaluate(rootNode, XPathConstants.NODESET);
            return nodes;
        } catch (XPathExpressionException e) {
            // We don't know why we would ever see this.
            // The XPath compiled OK.
            //
            // We would only expect to see this if the context (the rootNode)
            // is null, and the XPath was not contextless (contextless example: "1+1"),
            // but that would also be influenced by the return type, which we have
            // set as NODESET.
            //
            // So we _would_ (we do) get this if it's a node-like XPath and
            // the Node is null - see Javadocs for XPathExpression#evaluate(Object)

            // TODO: ICM 2025-12-31: Improve/change: would it be better to disallow null node?

            // So return an empty node list, rather than throwing an exception
            String message = "Unexpected problem evaluating XPath expression " + this.xPath;
            logger.error(message, e); // Currently this logging is disabled in unit tests

            return emptyNodeList();
        }
    }

    private XPathExpression createXpathExpression(String xPath) throws XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPathObject = xPathFactory.newXPath();
        return xPathObject.compile(xPath);
    }

}
