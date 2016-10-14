package org.ayeseeem.dpick.xml;

import org.w3c.dom.Node;

/**
 * Matches some expectation against an XML Node tree
 *
 * @author ayeseeem@gmail.com
 *
 */
interface XmlNodeMatcher {
    void match(Node rootNode);
}
