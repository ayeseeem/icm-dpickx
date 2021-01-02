package org.ayeseeem.dpick.app;

import static org.ayeseeem.dpick.xml.NodeMatchers.xpath;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.xpath.XPathExpressionException;

import org.ayeseeem.dpick.util.xml.XmlUnmarshaller;
import org.ayeseeem.dpick.xml.XmlDocumentChecker;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * The main entry point to the tool. Compare with {@link Application}.
 */
public class App {

    private final String file;
    private final String xpath;
    
    public App(String file, String xpath) throws XPathExpressionException, FileNotFoundException, IOException, SAXException {
        this.file = file;
        this.xpath = xpath;
        
        captureAndPrint();
    }

    private void captureAndPrint() throws XPathExpressionException, FileNotFoundException, IOException, SAXException {
        Node docRoot = makeDoc(file);

        XmlDocumentChecker checker = new XmlDocumentChecker(docRoot);

        String content = checker.captureSoleRequired(xpath(xpath));
        System.out.println(content);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Need 2 args: <filename> <XPath>");
            System.out.println(" - Xpath might need (Java) escaping of characters such as \\");
            return;
        }
        
        String file = args[0];
        String xpath = args[1];

        try {
            new App(file, xpath);
        } catch (XPathExpressionException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Node makeDoc(String file) throws FileNotFoundException, IOException, SAXException {
        XmlUnmarshaller u = new XmlUnmarshaller();
        try (FileInputStream fis = new FileInputStream(file)) {
            Document doc = u.toDocument(fis);
            return doc;
        }
    }

}
