package org.ayeseeem.dpick.xml;

import org.w3c.dom.Node;

/**
 * Allows applying operations such as assertions or other actions to DOM
 * {@link Node}s
 */
public interface NodeActions {
    NodeActions andExpect(NodeMatcher matcher);
    NodeActions andDo(NodeHandler handler);
}
