package org.ayeseeem.dpick.app;

import static org.ayeseeem.dpick.xml.NodeMatchers.xpath;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.xml.xpath.XPathExpressionException;

import org.ayeseeem.dpick.util.xml.XmlUnmarshaller;
import org.ayeseeem.dpick.xml.StringCapturer;
import org.ayeseeem.dpick.xml.XmlDocumentChecker;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * The main entry point to the tool.
 */
public class App {

    public static final int FILE_NOT_FOUND__ERROR = 2;
    public static final String FILE_NOT_FOUND__MESSAGE = "File Not Found";

    private final PrintStream out;
    private final String file;
    private final String xpath;

    public App(PrintStream out, String file, String xpath, boolean asInteger) throws XPathExpressionException, FileNotFoundException, IOException, SAXException {
        this.out = out;
        this.file = file;
        this.xpath = xpath;

        captureAndPrint(asInteger);
    }

    private void captureAndPrint(boolean asInteger) throws XPathExpressionException, FileNotFoundException, IOException, SAXException {
        Node docRoot = makeDoc(file);

        XmlDocumentChecker checker = new XmlDocumentChecker(docRoot);

        final Function<String, ?> converter;
        if (asInteger) {
            converter = Integer::parseInt;
        } else {
            converter = StringCapturer.NOOP;
        }
        Object content = checker.captureSoleRequired(xpath(xpath), converter);
        out.println(content);
    }

    public static void main(String[] args) {
        try {
            mainExecutor(args, System.out);
        } catch (FileNotFoundException e) {
            System.err.println(FILE_NOT_FOUND__MESSAGE + ": " + e.getMessage());
            System.exit(FILE_NOT_FOUND__ERROR);
        } catch (XPathExpressionException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    static void mainExecutor(String[] args, PrintStream out)
            throws XPathExpressionException, FileNotFoundException, IOException, SAXException {
        if (args.length < 2) {
            out.println("Need at least 2 args: <filename> <XPath> [-i] [--integer]");
            out.println(" - Xpath might need (Java) escaping of characters such as \\");
            return;
        }

        String file = args[0];
        String xpath = args[1];

        List<String> argList = Arrays.asList(args);
        boolean asInteger = argList.contains("-i") || argList.contains("--integer");
        new App(out, file, xpath, asInteger);
    }

    private Node makeDoc(String file) throws FileNotFoundException, IOException, SAXException {
        try (FileInputStream fis = new FileInputStream(file)) {
            XmlUnmarshaller u = new XmlUnmarshaller();
            Document doc = u.toDocument(fis);
            return doc;
        }
    }

}
