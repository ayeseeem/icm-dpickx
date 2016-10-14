package org.ayeseeem.dpick.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class XpathHelperTest {

    @Test
    public void testCreateEmptyNodeList() {
        assertNotNull(XpathHelper.createEmptyNodeList());
        assertEquals(0, XpathHelper.createEmptyNodeList().getLength());
    }

}
