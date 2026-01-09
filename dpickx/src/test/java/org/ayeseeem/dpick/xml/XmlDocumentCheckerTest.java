package org.ayeseeem.dpick.xml;

import org.junit.Test;

public class XmlDocumentCheckerTest {

    @Test(expected = NullPointerException.class)
    public void testXmlDocumentChecker_RequiresNonNullRootNode() {
        new XmlDocumentChecker(null);
    }

}
