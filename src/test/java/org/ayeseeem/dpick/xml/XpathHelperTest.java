package org.ayeseeem.dpick.xml;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class XpathHelperTest {

    @Test
    public void testCreateEmptyNodeList() {
        assertThat(XpathHelper.createEmptyNodeList(), is(not(nullValue())));
        assertThat(XpathHelper.createEmptyNodeList().getLength(), is(0));
    }

}
