package org.ayeseeem.dpick.xml;

import java.util.function.Function;

import org.w3c.dom.Node;

public class StringCapturer extends Capturer<String> {

    public static final Function<String, String> NOOP = s -> s;

    public StringCapturer() {
        super(NOOP);
    }

    static String convertToString(Node node) {
        return node.getTextContent();
    }

}
