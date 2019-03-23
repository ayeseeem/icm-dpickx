package org.ayeseeem.dpick.util.xml;

import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * Helper for examining XML and HTML DOMs
 *
 * @author ayeseeem@gmail.com
 *
 */
public class DomDump {

    private static final Logger logger = LoggerFactory.getLogger(DomDump.class);

    /**
     * Safely prints (part of) a DOM to the console, logging any errors
     *
     * @param node
     *            part of DOM to print
     */
    public static void safeDumpToConsole(Node node) {
        safeDump(node, System.out);
    }

    /**
     * Safely prints (part of) a DOM, logging any errors
     *
     * @param node
     *            part of DOM to print
     * @param sink
     *            where to print to
     */
    public static void safeDump(Node node, OutputStream sink) {
        try {
            dump(node, sink);
        } catch (TransformerFactoryConfigurationError | TransformerException e) {
            logger.error("Problem dumping DOM", e);
        }
    }

    // TODO: ICM 2016-10-29: Separate into constructor with exceptions and dump with exceptions
    private static void dump(Node node, OutputStream sink)
            throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        int indentationAmount = 2;
        Transformer transformer = makeIndentingTransformer(indentationAmount);

        DOMSource source = new DOMSource(node);
        StreamResult outputTarget = new StreamResult(sink);
        transformer.transform(source, outputTarget);
    }

    private static Transformer makeIndentingTransformer(int indentationAmount)
            throws TransformerConfigurationException, TransformerFactoryConfigurationError {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        setAsIndenting(transformer, indentationAmount);
        return transformer;
    }

    private static void setAsIndenting(Transformer transformer, int indentationAmount) {
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(indentationAmount));
    }

}
