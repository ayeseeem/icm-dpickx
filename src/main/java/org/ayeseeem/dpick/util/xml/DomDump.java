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
 * Helper for examing XML
 *
 * @author ayeseeem@gmail.com
 *
 */
public class DomDump {

    private static final Logger logger = LoggerFactory.getLogger(DomDump.class);

    /**
     * Safely prints part of an XML DOM to the console, logging any errors
     *
     * @param node
     *            part of XML DOM to print
     */
    public static void safeDumpToConsole(final Node node) {
        safeDump(node, System.out);
    }

    /**
     * Safely prints part of an XML DOM, logging any errors
     *
     * @param node
     *            part of XML DOM to print
     * @param sink
     *            where to print to
     */
    public static void safeDump(final Node node, final OutputStream sink) {
        try {
            dump(node, sink);
        } catch (TransformerFactoryConfigurationError | TransformerException e) {
            logger.error("Problem dumping XML", e);
        }
    }

    // TODO: ICM 2016-10-29: Separate into constructor with exceptions and dump with exceptions
    private static void dump(final Node node, final OutputStream sink)
            throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(node);
        StreamResult console = new StreamResult(sink);
        transformer.transform(source, console);
    }

}
