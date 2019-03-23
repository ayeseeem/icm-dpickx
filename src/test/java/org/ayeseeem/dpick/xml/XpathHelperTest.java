package org.ayeseeem.dpick.xml;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class XpathHelperTest {

    @Test
    public void testCreateEmptyNodeList() {
        assertNotNull(XpathHelper.createEmptyNodeList());
        assertThat(XpathHelper.createEmptyNodeList().getLength(), is(0));
    }

}
