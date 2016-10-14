package org.ayeseeem.dpick.xml;

import org.w3c.dom.Node;

/**
 * Applies some generic operation against an XML Node tree
 *
 * @author ayeseeem@gmail.com
 *
 */
interface XmlNodeHandler {
    void handle(Node rootNode);
}
