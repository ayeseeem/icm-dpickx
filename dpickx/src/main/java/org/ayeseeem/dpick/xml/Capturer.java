package org.ayeseeem.dpick.xml;

import java.util.Optional;

import org.w3c.dom.Node;

// TODO: ICM 2016-10-23: Templatize and require a converter function
// TODO: ICM 2016-10-23: make 2 versions - create thing(s) from string, create thing(s) from root node
public class Capturer {

    private Optional<String> value = Optional.empty();

    public void capture(Node node) {
        value = Optional.of(convertToString(node));
    }

    public Optional<String> value() {
        return value;
    }

    static String convertToString(Node node) {
        return node.getTextContent();
    }

}
