package org.ayeseeem.dpick.xml;

import java.util.Optional;
import java.util.function.Function;

import org.w3c.dom.Node;

//TODO: ICM 2016-10-23: make 2 versions - create thing(s) from string, create thing(s) from root node
public class Capturer <T> {

    private final Function<String, T> converter;

    private Optional<T> value = Optional.empty();

    public Capturer(Function<String, T> converter) {
        this.converter = converter;
    }

    public void capture(Node node) {
        value = Optional.of(converter.apply(node.getTextContent()));
    }

    public Optional<T> value() {
        return value;
    }

}
