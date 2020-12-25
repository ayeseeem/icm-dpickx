package org.ayeseeem.dpick.util.dom;

import static java.util.Objects.requireNonNull;

import java.util.AbstractList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Wraps a {@link NodeList} as an unmodifiable {@link List}, primarily so that
 * it implements {@link Iterable}, so it can 'be the target of the "foreach"
 * statement'.
 */
public class ListOfNode extends AbstractList<Node> {

    private final NodeList nodes;

    /**
     * Wraps a {@code NodeList} as a {@code ListOfNode}.
     *
     * @param nodes
     *            the node list to wrap - cannot be null
     * @return a new wrapper for the node list
     * @throws NullPointerException
     *             if the node list is null
     */
    public static ListOfNode of(NodeList nodes) {
        requireNonNull(nodes);

        return new ListOfNode(nodes);
    }

    private ListOfNode(NodeList nodes) {
        this.nodes = nodes;
    }

    @Override
    public Node get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }

        return nodes.item(index);
    }

    @Override
    public int size() {
        return nodes.getLength();
    }

}
