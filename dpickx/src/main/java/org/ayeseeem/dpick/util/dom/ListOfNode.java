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

    /**
     * Finds the last index of the node.
     * <p>
     * The DOM doesn't generally allow nodes to be inserted more than once in the
     * tree (it removes the duplicate when adding again). Our implementation defers
     * to abstract list, and @code Node uses == for equals, so we never expect to
     * find a node in more than one location (index). So we expect @code indexOf
     * and @code lastIndexOf to always be the same.
     */
    @Override
    public int lastIndexOf(Object node) {
        int lastIndex = super.lastIndexOf(node);
        assert lastIndex == super.indexOf(node);
        return lastIndex;
    }

}
