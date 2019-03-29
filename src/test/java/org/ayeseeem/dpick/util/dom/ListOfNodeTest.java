package org.ayeseeem.dpick.util.dom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ListOfNodeTest {

    @Test
    public void testSize() {
        assertThat(emptyListOfNode().size(), is(0));
        assertThat(randomListOfNode().size(), is(1));
    }

    @Test
    public void testOf() {
        assertThat(ListOfNode.of(DomBuilder.emptyNodeList()), is(empty()));

        ListOfNode result = ListOfNode.of(randomNodeList());
        assertThat(result, contains(result.get(0)));
    }

    @Test(expected = NullPointerException.class)
    public void testOf_RequiresNonNull() {
        ListOfNode.of(null);
    }

    @Test
    public void testGet() {
        NodeList nodes = randomNodeList();
        ListOfNode subject = ListOfNode.of(nodes);
        assertThat(subject.get(0), is(not(nullValue())));
        assertThat(subject.get(0), is(nodes.item(0)));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet_IndexTooSmall() {
        ListOfNode subject = randomListOfNode();
        subject.get(-1);
    }

    @Test
    public void testGet_IndexTooSmall_ErrorMessage() {
        ListOfNode subject = randomListOfNode();
        try {
            subject.get(-123);
            fail("should have thrown");
        } catch (Throwable e) {
            assertThat(e.getMessage(), is("-123"));
        }
    }

    @Test
    public void testGet_IndexTooSmall_ErrorMessage_MatchesNormalList() {
        ListOfNode subject = randomListOfNode();
        try {
            subject.get(-123);
            fail("should have thrown");
        } catch (Throwable e) {
            assertThat(e.getMessage(), is(getStandardOutOfBoundsdMessage(-123)));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet_IndexTooLarge() {
        ListOfNode subject = randomListOfNode();
        subject.get(subject.size() + 100);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet_IndexJustTooLarge() {
        ListOfNode subject = randomListOfNode();
        assertThat(subject.get(0), is(not(nullValue())));
        assertThat(subject.get(subject.size() - 1), is(not(nullValue())));
        subject.get(subject.size());
    }

    @Test
    public void testGet_IndexTooLarge_ErrorMessage() {
        ListOfNode subject = randomListOfNode();
        try {
            subject.get(123);
            fail("should have thrown");
        } catch (Throwable e) {
            assertThat(e.getMessage(), is("123"));
        }
    }

    @Test
    public void testGet_IndexTooLarge_ErrorMessage_MatchesNormalList() {
        ListOfNode subject = randomListOfNode();
        try {
            subject.get(123);
            fail("should have thrown");
        } catch (Throwable e) {
            assertThat(e.getMessage(), is(getStandardOutOfBoundsdMessage(123)));
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        ListOfNode subject = emptyListOfNode();
        subject.add(randomNode());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testClear() {
        ListOfNode subject = randomListOfNode();
        subject.clear();
    }

    @Test
    public void testClear_WorksOnEmptyList() {
        ListOfNode subject = emptyListOfNode();
        subject.clear();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSet() {
        ListOfNode subject = emptyListOfNode();
        subject.set(0, randomNode());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove_ByIndex() {
        ListOfNode subject = randomListOfNode();
        subject.remove(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove_Object() {
        ListOfNode subject = randomListOfNode();
        subject.remove(subject.get(0));
    }

    @Test
    public void testRemove_Object_WorksIfObjectNotPresent() {
        ListOfNode subject = randomListOfNode();
        subject.remove(randomNode());
    }

    @Test
    public void testIndexOf() {
        ListOfNode subject = randomListOfNode();
        assertThat(subject.indexOf(subject.get(0)), is(0));
        assertThat(subject.indexOf(randomNode()), is(-1));
    }

    @Test
    public void testLastIndexOf() {
        // TODO: ICM 2019-03-24: Verify it's the _last_ index, not just _an_ index
        ListOfNode subject = randomListOfNode();
        assertThat(subject.indexOf(subject.get(0)), is(0));
        assertThat(subject.indexOf(randomNode()), is(-1));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() {
        ListOfNode subject = randomListOfNode();
        subject.addAll(randomListOfNode());
    }

    @Test
    public void testListIterator() {
        ListOfNode subject = randomListOfNode();
        Iterator<Node> it = subject.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(subject.get(0)));
        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void testSubList() {
        ListOfNode subject = randomListOfNode();

        assertThat(subject.subList(0, 0), is(empty()));

        assertThat(subject.subList(0, 1), contains(subject.get(0)));
    }

    @Test
    public void testToString() {
        ListOfNode subject = randomListOfNode();
        assertThat(subject.toString(), is("[" + subject.get(0).toString() + "]"));
    }

    @Test
    public void testIsEmpty() {
        assertThat(emptyListOfNode().isEmpty(), is(true));
        assertThat(randomListOfNode().isEmpty(), is(false));
    }

    @Test
    public void testContains() {
        ListOfNode subject = randomListOfNode();

        assertThat(subject.contains(subject.get(0)), is(true));
        assertThat(subject.contains(randomNode()), is(false));
    }

    @Test
    public void testToArray() {
        assertThat(emptyListOfNode().toArray().length, is(0));

        ListOfNode subject = randomListOfNode();
        assertThat(subject.toArray().length, is(1));
        assertThat(subject.toArray()[0], is(subject.get(0)));
    }

    @Test
    public void testContainsAll() {
        ListOfNode subject = randomListOfNode();

        Node node0 = subject.get(0);
        assertThat(subject.containsAll(Arrays.asList(node0)), is(true));
        assertThat(subject.containsAll(Arrays.asList(randomNode())), is(false));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAll() {
        ListOfNode subject = randomListOfNode();
        subject.removeAll(subject);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRetainAll() {
        ListOfNode subject = randomListOfNode();
        subject.retainAll(Arrays.asList(randomNode()));
    }

    @Test
    public void testRetainAll_WorksIfNoChangeIsMade() {
        ListOfNode subject = randomListOfNode();

        Node node0 = subject.get(0);
        assertThat(subject.retainAll(Arrays.asList(node0)), is(false));
    }

    private static ListOfNode emptyListOfNode() {
        return ListOfNode.of(DomBuilder.emptyNodeList());
    }

    private static ListOfNode randomListOfNode() {
        return ListOfNode.of(randomNodeList());
    }

    private static NodeList randomNodeList() {
        Document doc = DomBuilder.emptyDocument();

        Element root = doc.createElement("RootElement" + UUID.randomUUID().toString());
        doc.appendChild(root);

        NodeList nodes = doc.getChildNodes();
        assert nodes.getLength() != 0;
        return nodes;
    }

    private static Node randomNode() {
        return randomNodeList().item(0);
    }

    private String getStandardOutOfBoundsdMessage(int outOfBoundsIndex) {
        try {
            Arrays.asList().get(outOfBoundsIndex);
            throw new Error("should have thrown");
        } catch (Throwable e) {
            return e.getMessage();
        }
    }

}
