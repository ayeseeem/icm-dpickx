package org.ayeseeem.dpick.util.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlUnmarshaller {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final boolean namespaceAware;

    /**
     * Creates an XML unmarshaller without namespace awareness
     */
    public XmlUnmarshaller() {
        this(false);
    }

    /**
     * Creates an XML unmarshaller without namespace awareness
     *
     * @param namespaceAware
     *            {@code true} to be namespace aware
     */
    public XmlUnmarshaller(boolean namespaceAware) {
        this.namespaceAware = namespaceAware;
    }

    public boolean isNamespaceAware() {
        return namespaceAware;
    }

    /**
     * Creates an XML Document
     *
     * @param xmlString
     *            the XML (as a string)
     * @return the XML Document
     * @throws SAXException
     *             If any parse errors occur. This is an expected sort of error,
     *             for when non-XML or invalid XML is passed in
     */
    public Document toDocument(String xmlString) throws SAXException {
        Document document = createNormalizedDom(xmlString);
        return document;
    }

    /**
     * Creates an XML Document
     *
     * @param xmlSource
     *            source of the XML
     * @return the XML Document
     * @throws SAXException
     *             If any parse errors occur. This is an expected sort of error,
     *             for when non-XML or invalid XML is passed in
     * @throws IOException
     *             If there is a problem reading the XML source stream
     */
    public Document toDocument(InputStream xmlSource) throws SAXException, IOException {
        Document document = createNormalizedDom(xmlSource);
        return document;
    }

    /**
     * Creates a DOM from a string (of XML)
     *
     * @param xmlString
     *            the XML (as a string)
     * @return an XML Document
     * @throws SAXException
     *             If any parse errors occur. This is an expected sort of error,
     *             for when non-XML or invalid XML is passed in
     */
    private Document createNormalizedDom(String xmlString) throws SAXException{
        // TODO: ICM 2015-09-16: Specifying the encoding as UTF-8 - should we check that it actually is UTF-8 first?
        final ByteArrayInputStream is = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
        try {
            return createNormalizedDom(is);
        } catch (IOException e) {
            String message = "Exception should not be possible" + ": " + "There should not be an IOException when processing a String";
            logger.error(message, e);
            throw new IllegalStateException(message, e);
        }
    }

    /**
     * Creates a DOM from a string (of XML)
     *
     * @param xmlSource
     *            source of the XML
     * @return an XML Document
     * @throws SAXException
     *             If any parse errors occur. This is an expected sort of error,
     *             for when non-XML or invalid XML is passed in
     * @throws IOException
     */
    private Document createNormalizedDom(InputStream xmlSource) throws SAXException, IOException {
        // TODO: ICM 2015-09-17: Validate the XML using a schema? See https://docs.oracle.com/javase/tutorial/jaxp/dom/validating.html
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(namespaceAware);
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // Thrown "if a DocumentBuilder cannot be created which satisfies the configuration requested".
            // This should never happen, we should have a fixed configuration that always works.
            final String message = "Configuration Error: Cannot create DocumentBuilder";
            logger.error(message, e);
            throw new IllegalStateException(message, e);
        }
        dBuilder.setErrorHandler(new LoggingErrorHandler());
        Document document = dBuilder.parse(xmlSource);
        document.getDocumentElement().normalize();
        return document;
    }

    /**
     * When passed to a document builder, prevents it writing errors to
     * {@code System.err}. Instead, it logs various types of errors and
     * warnings.
     *
     * Only fatal errors result in the {@code SAXException} being propagated.
     */
    public class LoggingErrorHandler implements ErrorHandler {
        @Override
        public void warning(SAXParseException e) throws SAXException {
            logger.warn(e.getMessage());
            // don't propagate: matches DocumentBuilder's default behaviour
        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException {
            logger.error("FATAL: " + e.getMessage());
            // propagate: matches DocumentBuilder's default behaviour:
            throw e;
        }

        @Override
        public void error(SAXParseException e) throws SAXException {
            logger.error(e.getMessage());
            // don't propagate: matches DocumentBuilder's default behaviour
        }
    }

}
