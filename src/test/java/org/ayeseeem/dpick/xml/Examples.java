package org.ayeseeem.dpick.xml;

import static org.ayeseeem.dpick.matchers.ConvertibleStringMatchers.numberOfValue;
import static org.ayeseeem.dpick.xml.NodeMatchers.xpath;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.hamcrest.core.StringStartsWith;
import org.junit.Test;
import org.w3c.dom.Node;

/**
 * Examples of usage as JUnit tests. For more examples, see the
 * {@link IntegrationTest}s.
 *
 * @author ayeseeem@gmail.com
 *
 */
public class Examples extends XmlExampleFixture {

    @Test
    public void exampleComplexCheck_ValidXml() throws XPathExpressionException {
        XmlDocumentChecker.check(eg)
                .andExpect(xpath("/RootElement/SomethingUnique").exists())
                .andExpect(xpath("//NeverExisting").doesNotExist())
                .andExpect(xpath("//Repeated").exists())
                .andExpect(xpath("//Repeated").nodeCount(2))
                .andExpect(xpath("/NeverExistingTopLevel").doesNotExist())
                .andExpect(xpath("//ContainsSeventeen").value(is("17")))
                .andExpect(xpath("//ContainsSeventeen").value(containsString("7")))
                .andExpect(xpath("//ContainsSeventeen").value(StringStartsWith.startsWith("1")))
                .andExpect(xpath("//ContainsSeventeen").value(is(numberOfValue(17.0))))
                .andExpect(xpath("//ElementWithSizeAttribute/@size").exists())
                .andExpect(xpath("//ElementWithSizeAttribute/@size").value(is("15")))
                ;
    }

    @Test
    public void exampleHandlingXmlProblem() throws XPathExpressionException {
        try {
            XmlDocumentChecker.check(eg).andExpect(xpath("/Version").value(is("3.0")));

            fail("example/test should not reach here");
        } catch (AssertionError e) {
            @SuppressWarnings("unused")
            final String status = "Document is not Version 3";
            // Do something about it
        }
    }

    @Test
    public void exampleProcessingSelectedNodes() throws XPathExpressionException {
        List<Node> fakeConsumer = new ArrayList<>();

        XmlDocumentChecker.check(eg)
                .andExpect(xpath("//Repeated").nodeCount(2))
                .andDo(xpath("//Repeated").processEach(node -> fakeConsumer.add(node)))
                .andDo(xpath("//Repeated").processEach(node -> {
                    // do something complicated with node
                }))
                ;

        assertThat(fakeConsumer.size(), is(2));
    }

    @Test
    public void exampleCapturingRequiredValue() throws XPathExpressionException {
        XmlDocumentChecker checker = new XmlDocumentChecker(eg);
        String drivingAge = checker.captureSoleRequired(xpath("//ContainsSeventeen"));

        // do something with captured value...
        assertThat(drivingAge, is("17"));
    }

}
