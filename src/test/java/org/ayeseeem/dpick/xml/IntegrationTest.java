package org.ayeseeem.dpick.xml;

import static org.ayeseeem.dpick.matchers.ConvertibleStringMatchers.booleanOfValue;
import static org.ayeseeem.dpick.matchers.ConvertibleStringMatchers.numberOfValue;
import static org.ayeseeem.dpick.xml.NodeMatchers.xpath;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Node;

/**
 * Test all main usages using end-to-end examples
 *
 * @author ayeseeem@gmail.com
 *
 */
public class IntegrationTest extends XmlExampleFixture {

    @Test
    public void expect_Exists_RootElement() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("/RootElement").exists());
    }

    @Test
    public void expect_DoesNotExist() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("/NeverExisting").doesNotExist());
    }

    @Test
    public void expect_Exists_ButDoesNotExist() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("XPath /NeverExisting does not exist");

        XmlDocumentChecker.check(eg).andExpect(xpath("/NeverExisting").exists());
    }

    @Test
    public void expect_DoesNotExist_ButDoesExist() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("XPath /RootElement exists");

        XmlDocumentChecker.check(eg).andExpect(xpath("/RootElement").doesNotExist());
    }

    @Test
    public void expect_Exists_SubElement_ByFullPath() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("/RootElement/SomethingUnique").exists());
    }

    @Test
    public void expect_Exists_SubElement_ByPartialPath() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//SomethingUnique").exists());
    }

    @Test
    public void expect_Exists_Attribute() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//ElementWithSizeAttribute/@size").exists());
        XmlDocumentChecker.check(eg).andExpect(xpath("/RootElement/ElementWithSizeAttribute/@size").exists());
    }

    @Test
    public void expect_DoesNotExist_Attribute() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//ElementWithSizeAttribute/@nonExistentAttribute").doesNotExist());
        XmlDocumentChecker.check(eg).andExpect(xpath("/RootElement/ElementWithSizeAttribute/@nonExistentAttribute").doesNotExist());
    }

    @Test
    public void expect_NodeCount() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//Repeated").nodeCount(2));
    }

    @Test
    public void expect_NodeCount_OfZero_ForNonExistentElement() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//NeverExisting").nodeCount(0));
    }

    @Test
    public void expect_NodeCount_Wrong() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected 888 nodes for XPath //Repeated, not 2");

        XmlDocumentChecker.check(eg).andExpect(xpath("//Repeated").nodeCount(888));
    }

    @Test
    public void expect_Node_Matcher() throws XPathExpressionException {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("Not implemented yet");

        XmlDocumentChecker.check(eg).andExpect(xpath("//Repeated").node(null));
    }

    @Test
    public void expect_Number() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsSeventeen").value(is(numberOfValue(17.0))));
    }

    @Test
    public void expect_Number_WrongValue() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected: is value parsable as a number of value <888.0>");
        thrown.expectMessage("but: was \"17\"");

        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsSeventeen").value(is(numberOfValue(888.0))));
    }

    @Test
    public void expect_Number_NotANumber() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected: is value parsable as a number of value <888.0>");
        thrown.expectMessage("but: was \"blah blah\"");

        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsAttributeWithEighteen").value(is(numberOfValue(888.0))));
    }

    @Test
    public void expect_Value() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsSeventeen").value(is("17")));

        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsSeventeen").value(containsString("1")));
        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsSeventeen").value(containsString("7")));
    }

    @Test
    public void expect_Value_WrongValue() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected: is \"888\"");
        thrown.expectMessage("but: was \"17\"");

        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsSeventeen").value(is("888")));
    }

    @Test
    public void expect_Value_WrongValue_ErrorMessageIncludesXPath() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("//ContainsSeventeen");
        thrown.expectMessage("XPath //ContainsSeventeen");

        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsSeventeen").value(is("888")));
    }

    @Test
    public void expect_Value_NonExistentElement() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("XPath //NeverExisting does not exist");

        XmlDocumentChecker.check(eg).andExpect(xpath("//NeverExisting").value(null));
    }

    @Test
    public void expect_Value_MutlipleElements() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//Duplicate").nodeCount(2));

        XmlDocumentChecker.check(eg).andExpect(xpath("//Duplicate").value(is("123")));
    }

    @Test
    public void expect_Value_MutlipleElements_NotAllMatch() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected: is \"123\"");
        thrown.expectMessage("but: was \"456\"");
        XmlDocumentChecker.check(eg).andExpect(xpath("//DuplicateEleDiffContent").nodeCount(2));

        XmlDocumentChecker.check(eg).andExpect(xpath("//DuplicateEleDiffContent").value(is("123")));
    }

    @Test
    public void expect_Value_Attribute() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsAttributeWithEighteen/@attrOf18").value(is("18")));
    }

    @Test
    public void expect_Value_Attribute_WrongValue() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected: is \"888\"");
        thrown.expectMessage("but: was \"18\"");

        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsAttributeWithEighteen/@attrOf18").value(is("888")));
    }

    @Test
    public void expect_Value_NonExistentAttribute() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("XPath //ContainsAttributeWithEighteen/@nonExistentAttribute does not exist");

        XmlDocumentChecker.check(eg).andExpect(xpath("//ContainsAttributeWithEighteen/@nonExistentAttribute").value(is("888")));
    }

    @Test
    public void invalidXpath() throws XPathExpressionException {
        thrown.expect(XPathExpressionException.class);
        thrown.expectCause(instanceOf(TransformerException.class));

        XmlDocumentChecker.check(eg).andExpect(xpath("This is an invalid XPath").exists());
    }

    @Test
    public void expect_Value_BooleanMatcher() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("//AlwaysTrue").value(is(booleanOfValue(true))));
    }

    @Test
    public void do_ProcessEach() throws XPathExpressionException {
        List<String> spy = new ArrayList<>();

        XmlDocumentChecker.check(eg)
                .andExpect(xpath("//DuplicateEleDiffContent").nodeCount(2))
                .andDo(xpath("//DuplicateEleDiffContent")
                        .processEach(node -> spy.add(node.getTextContent())));

        assertThat(spy, is(Arrays.asList("123", "456")));
    }

    @Test
    public void do_ProcessEach_NoNodesFound() throws XPathExpressionException {
        List<Node> spy = new ArrayList<>();

        XmlDocumentChecker.check(eg)
                .andExpect(xpath("//NeverExisting").nodeCount(0))
                .andDo(xpath("//NeverExisting").processEach(spy::add));

        assertThat(spy.isEmpty(), is(true));
    }

    @Test
    public void direct_CaptureSoleRequired() throws XPathExpressionException {
        XmlDocumentChecker checker = new XmlDocumentChecker(eg);
        String textValue = checker.captureSoleRequired(xpath("//ContainsSeventeen"));

        assertThat(textValue, is("17"));
    }

    @Test
    public void direct_CaptureSoleRequired_MoreThanOneNodeFound() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected 1 nodes for XPath //Repeated, not 2");

        XmlDocumentChecker checker = new XmlDocumentChecker(eg);
        checker.captureSoleRequired(xpath("//Repeated"));
    }

    @Test
    public void direct_CaptureSoleRequired_NonExistentElement() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("XPath //NeverExisting does not exist");

        XmlDocumentChecker checker = new XmlDocumentChecker(eg);
        checker.captureSoleRequired(xpath("//NeverExisting"));
    }

    @Test
    public void do_CaptureSoleRequired() throws XPathExpressionException {
        final Capturer capturer = new Capturer();
        assertThat(capturer.value().isPresent(), is(false));

        XmlDocumentChecker.check(eg)
                .andDo(xpath("//ContainsSeventeen").captureSoleRequired(capturer));

        assertThat(capturer.value().isPresent(), is(true));
        assertThat(capturer.value().get(), is("17"));
    }

    @Test
    public void do_CaptureSoleRequired_MoreThanOneNodeFound() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Expected 1 nodes for XPath //Repeated, not 2");

        XmlDocumentChecker.check(eg)
                .andDo(xpath("//Repeated").captureSoleRequired(null));
    }

    @Test
    public void do_CaptureSoleRequired_NonExistentElement() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("XPath //NeverExisting does not exist");

        XmlDocumentChecker.check(eg)
                .andDo(xpath("//NeverExisting").captureSoleRequired(null));
    }

    @Test
    public void do_CaptureSoleRequired_Attribute() throws XPathExpressionException {
        final Capturer capturer = new Capturer();

        XmlDocumentChecker.check(eg)
                .andDo(xpath("//ElementWithSizeAttribute/@size").captureSoleRequired(capturer));

        assertThat(capturer.value().get(), is("15"));
    }

    @Test
    public void do_CaptureSoleRequired_NonExistentAttribute() throws XPathExpressionException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("XPath //ElementWithSizeAttribute/@nonExistentAttribute does not exist");

        XmlDocumentChecker.check(eg)
                .andDo(xpath("//ElementWithSizeAttribute/@nonExistentAttribute").captureSoleRequired(null));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

}
