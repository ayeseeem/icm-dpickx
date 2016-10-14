package org.ayeseeem.dpick.xml;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class XpathHelperTest {

    @Test
    public void testCreateEmptyNodeList() {
        assertNotNull(XpathHelper.createEmptyNodeList());
        assertThat(XpathHelper.createEmptyNodeList().getLength(), is(0));
    }

}
