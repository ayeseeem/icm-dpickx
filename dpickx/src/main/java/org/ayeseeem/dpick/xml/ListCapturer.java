package org.ayeseeem.dpick.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

public class ListCapturer {

    private List<String> values = new ArrayList<>();

    // TODO: ICM 2026-01-10: Should we only have one method: either capture list, or node, not both
    public void capture(List<Node> nodes) {
        for (Node node : nodes) {
            capture(node);
        }
    }

    public void capture(Node node) {
        values.add(Capturer.convertToString(node));
    }

    public List<String> value() {
        return values;
    }

}
