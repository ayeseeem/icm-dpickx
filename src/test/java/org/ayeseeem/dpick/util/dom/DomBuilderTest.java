package org.ayeseeem.dpick.util.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.w3c.dom.NodeList;

public class DomBuilderTest {

    @Test
    public void testEmptyDocument() {
        assertThat(DomBuilder.emptyDocument().getDoctype(), is(nullValue()));
        assertThat(DomBuilder.emptyDocument().getAttributes(), is(nullValue()));
        assertThat(DomBuilder.emptyDocument().getDocumentElement(), is(nullValue()));
    }

    @Test
    public void testEmptyDocument_DocumentHasNoChildNodes() {
        assertThat(DomBuilder.emptyDocument().getChildNodes().getLength(), is(0));
    }

    @Test
    public void testEmptyNodeList() {
        assertThat(DomBuilder.emptyNodeList(), is(not(nullValue())));
        assertThat(DomBuilder.emptyNodeList().getLength(), is(0));
    }

    @Test
    public void testEmptyNodeList_CreatesNewObjects() {
        NodeList empty1 = DomBuilder.emptyNodeList();
        NodeList empty2 = DomBuilder.emptyNodeList();

        assertThat(empty1, is(not(sameInstance(empty2))));
    }

}
