package org.ayeseeem.dpick.xml;

import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

/**
 * Factory methods for creating Node matchers based on various selection
 * mechanisms. Currently only XPath supported.
 *
 * @author ayeseeem@gmail.com
 *
 */
public class NodeMatchers {

    /**
     * The XPath expression can be a parameterized string using formatting
     * specifiers as defined in {@link String#format(String, Object...)}.
     *
     * @param xPathExpression
     *            the XPath, optionally parameterized with arguments
     * @param xPathExpressionargs
     *            arguments to parameterize the XPath expression with
     * @return an {@link XpathNodeMatchers} that allows assertions on the node
     *         specified by the XPath
     * @throws XPathExpressionException
     *             if there is a problem parsing the XPath expression
     */
    public static XpathNodeMatchers xpath(String xPathExpression, Object... xPathExpressionargs)
            throws XPathExpressionException {

        return new XpathNodeMatchers(xPathExpression, null, xPathExpressionargs);
    }

    /**
     * The XPath expression can be a parameterized string using formatting
     * specifiers as defined in {@link String#format(String, Object...)}.
     *
     * @param xPathExpression
     *            the XPath, optionally parameterized with arguments
     * @param namespaces
     *            namespaces referenced in the XPath expression
     * @param xPathExpressionargs
     *            arguments to parameterize the XPath expression with
     * @return an {@link XpathNodeMatchers} that allows assertions on the node
     *         specified by the XPath
     * @throws XPathExpressionException
     *             if there is a problem parsing the XPath expression
     */
    public static XpathNodeMatchers xpath(String xPathExpression, Map<String, String> namespaces, Object... xPathExpressionargs)
            throws XPathExpressionException {

        return new XpathNodeMatchers(xPathExpression, namespaces, xPathExpressionargs);
    }
}
