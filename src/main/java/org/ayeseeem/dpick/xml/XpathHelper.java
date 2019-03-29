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
 *
 * @author ayeseeem@gmail.com
 *
 */
public class XpathHelper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String xPath;
    private final XPathExpression expression;

    public XpathHelper(String xPath, Map<String, String> namespaces, Object... args) throws XPathExpressionException {
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
            // we don't know why we would ever see this. The XPath compiled OK:
            // we don't know why it would fail to evaluate.
            //
            // TODO: ICM 2016-10-14: Improve/change: can happen e.g. if Node is
            // an empty XML Document top-level element, e.g. created:
            // Document doc = builder.newDocument();
            // Node n = doc.getDocumentElement();
            //
            // So return an empty node list, rather than throwing an exception
            String message = "Unexpected problem evaluating XPath expression " + this.xPath;
            logger.error(message, e);

            return emptyNodeList();
        }
    }

    private XPathExpression createXpathExpression(String xPath) throws XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPathObject = xPathFactory.newXPath();
        return xPathObject.compile(xPath);
    }

}
