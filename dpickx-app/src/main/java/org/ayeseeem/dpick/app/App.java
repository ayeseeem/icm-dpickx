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

    private enum Type {
        INTEGER, NUMBER, STRING
    }

    public App(PrintStream out, String file, String xpath, Type type) throws XPathExpressionException, FileNotFoundException, IOException, SAXException {
        this.out = out;
        this.file = file;
        this.xpath = xpath;

        captureAndPrint(type);
    }

    private void captureAndPrint(Type asType) throws XPathExpressionException, FileNotFoundException, IOException, SAXException {
        Node docRoot = makeDoc(file);

        XmlDocumentChecker checker = new XmlDocumentChecker(docRoot);

        final Function<String, ?> converter;

        switch (asType) {
        case INTEGER:
            converter = Integer::parseInt;
            break;

        case NUMBER:
            converter = Double::parseDouble;
            break;

        case STRING:
        default:
            converter = StringCapturer.NOOP;
            break;
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
            out.println("Need at least 2 args: <filename> <XPath> [-i] [--integer] [-n] [--number]");
            out.println(" - Xpath might need (Java) escaping of characters such as \\");
            return;
        }

        String file = args[0];
        String xpath = args[1];

        List<String> argList = Arrays.asList(args);
        final Type type;
        if (argList.contains("-i") || argList.contains("--integer")) {
            type = Type.INTEGER;
        } else if (argList.contains("-n") || argList.contains("--number")) {
            type = Type.NUMBER;
        } else {
            type = Type.STRING;
        }
        new App(out, file, xpath, type);
    }

    private Node makeDoc(String file) throws FileNotFoundException, IOException, SAXException {
        try (FileInputStream fis = new FileInputStream(file)) {
            XmlUnmarshaller u = new XmlUnmarshaller();
            Document doc = u.toDocument(fis);
            return doc;
        }
    }

}
