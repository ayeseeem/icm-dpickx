package org.ayeseeem.dpick.util.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;

import java.util.List;

import org.junit.Test;
import org.w3c.dom.Node;

public class NodeListSupportTest {

    @Test
    public void testListOf() {
        assertThat(NodeListSupport.listOf(DomBuilder.emptyNodeList()), is(empty()));

        List<Node> result = NodeListSupport.listOf(ListOfNodeTest.randomNodeList());
        assertThat(result, contains(result.get(0)));
    }

    @Test(expected = NullPointerException.class)
    public void testListOf_RequiresNonNull() {
        NodeListSupport.listOf(null);
    }

}
