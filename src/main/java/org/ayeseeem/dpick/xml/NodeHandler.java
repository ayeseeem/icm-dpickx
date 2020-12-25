package org.ayeseeem.dpick.xml;

import org.w3c.dom.Node;

/**
 * Applies some generic operation against a DOM {@link Node} tree
 */
@FunctionalInterface
public interface NodeHandler {
    void handle(Node rootNode);
}
