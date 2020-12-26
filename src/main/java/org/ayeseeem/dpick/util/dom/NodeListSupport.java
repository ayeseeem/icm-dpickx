package org.ayeseeem.dpick.util.dom;

import static java.util.Objects.requireNonNull;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Provides helpers for {@link org.w3c.dom.NodeList}.
 */
public class NodeListSupport {

    /**
     * Presents a {@code NodeList} as a {@code List<Node>}, so that normal
     * {@code List} operations can be used. 
     *
     * @param nodes
     *            the node list to wrap - cannot be null
     * @return a new wrapper for the node list
     * @throws NullPointerException
     *             if the node list is null
     */
    public static List<Node> of(NodeList nodes) {
        requireNonNull(nodes);

        return new ListOfNode(nodes);
    }

}
