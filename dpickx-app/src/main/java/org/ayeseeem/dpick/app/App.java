package org.ayeseeem.dpick.app;

import static org.ayeseeem.dpick.xml.NodeMatchers.xpath;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.ayeseeem.dpick.util.xml.XmlUnmarshaller;
import org.ayeseeem.dpick.xml.XmlDocumentChecker;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * The main entry point to the tool.
 */
public class App {

    private final String file;
    private final String xpath;

    public App(String file, String xpath, boolean asInteger) throws XPathExpressionException, FileNotFoundException, IOException, SAXException {
        this.file = file;
        this.xpath = xpath;

        captureAndPrint(asInteger);
    }

    private void captureAndPrint(boolean asInteger) throws XPathExpressionException, FileNotFoundException, IOException, SAXException {
        Node docRoot = makeDoc(file);

        XmlDocumentChecker checker = new XmlDocumentChecker(docRoot);

        if (asInteger) {
            int content = checker.captureSoleRequired(xpath(xpath), Integer::parseInt);
            System.out.println(content);
        } else {
            String content = checker.captureSoleRequired(xpath(xpath));
            System.out.println(content);
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Need at least 2 args: <filename> <XPath> [-i] [--integer]");
            System.out.println(" - Xpath might need (Java) escaping of characters such as \\");
            return;
        }

        String file = args[0];
        String xpath = args[1];

        List<String> argList = Arrays.asList(args);
        boolean asInteger = argList.contains("-i") || argList.contains("--integer");

        try {
            new App(file, xpath, asInteger);
        } catch (XPathExpressionException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Node makeDoc(String file) throws FileNotFoundException, IOException, SAXException {
        try (FileInputStream fis = new FileInputStream(file)) {
            XmlUnmarshaller u = new XmlUnmarshaller();
            Document doc = u.toDocument(fis);
            return doc;
        }
    }

}
