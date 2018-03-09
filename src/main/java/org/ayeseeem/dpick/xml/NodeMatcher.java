package org.ayeseeem.dpick.xml;

import org.w3c.dom.Node;

/**
 * Matches some expectation against a DOM {@link Node} tree
 *
 * @author ayeseeem@gmail.com
 *
 */
interface NodeMatcher {
    void match(Node rootNode);
}