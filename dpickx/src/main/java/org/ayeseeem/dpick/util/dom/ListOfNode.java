package org.ayeseeem.dpick.util.dom;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Wraps a {@link NodeList} as an unmodifiable {@link List}, primarily so that
 * it implements {@link Iterable}, so it can 'be the target of the "foreach"
 * statement'.
 */
public class ListOfNode extends AbstractList<Node> implements List<Node> {

    private final NodeList nodes;

    ListOfNode(NodeList nodes) {
        this.nodes = nodes;
    }

    @Override
    public Node get(int index) {
        if (index < 0 || index >= size()) {
            List<Integer> listLikeBehaviour = Arrays.asList(new Integer[size()]);
            listLikeBehaviour.get(index);
        }

        return nodes.item(index);
    }

    @Override
    public int size() {
        return nodes.getLength();
    }

}
