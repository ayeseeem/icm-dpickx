package org.ayeseeem.dpick.xml;

/**
 * Allows applying operations such as assertions or other actions to XML Nodes
 *
 * @author ayeseeem@gmail.com
 *
 */
public interface XmlNodeActions {
    XmlNodeActions andExpect(XmlNodeMatcher matcher);
    XmlNodeActions andDo(XmlNodeHandler handler);
}
