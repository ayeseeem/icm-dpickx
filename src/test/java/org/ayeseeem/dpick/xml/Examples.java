package org.ayeseeem.dpick.xml;

import static org.ayeseeem.dpick.xml.NodeMatchers.xpath;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import javax.xml.xpath.XPathExpressionException;

import org.hamcrest.core.StringStartsWith;
import org.junit.Test;

/**
 * Examples of usage as JUnit tests
 *
 * @author ayeseeem@gmail.com
 *
 */
public class Examples extends XmlExampleFixture {

    @Test
    public void exampleComplexCheck_ValidXml() throws XPathExpressionException {
        XmlDocumentChecker.check(eg).andExpect(xpath("/RootElement/SomethingUnique").exists())
                .andExpect(xpath("//Repeated").exists())
                .andExpect(xpath("//Repeated").nodeCount(2))
                .andExpect(xpath("//NeverExisting").doesNotExist())
                .andExpect(xpath("/NeverExistingTopLevel").doesNotExist())
                .andExpect(xpath("//ElementWithSizeAttribute/@size").exists())
                .andExpect(xpath("//ContainsSeventeen").value(is("17")))
                .andExpect(xpath("//ContainsSeventeen").value(containsString("7")))
                .andExpect(xpath("//ContainsSeventeen").value(StringStartsWith.startsWith("1")))
                .andExpect(xpath("//ContainsSeventeen").number(17.0))
                ;
    }

    @Test
    public void exampleHandlingXmlProblem() throws XPathExpressionException {
        try {
            XmlDocumentChecker.check(eg).andExpect(xpath("/Version").number(3.0));

            fail("example/test should not reach here");
        } catch (AssertionError e) {
            @SuppressWarnings("unused")
            final String status = "Document is not Version 3";
            // Do something about it
        }
    }

}
