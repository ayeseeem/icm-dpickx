package org.ayeseeem.dpick.app;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.junit.Test;

/**
 * Test main app usages using end-to-end examples,
 */
public class AppTest {

    // TODO: ICM 2026-01-19: De-duplicate - defined in XmlExampleFixtureTest too
    private static final String EXAMPLE_XML = "../dpickx-app/example.xml";

    @Test
    public void noArgs() throws Exception {
        App.mainExecutor(new String[] {}, out);

        assertThat(capturedOut.toString(), containsString("Need at least 2 args:"));
        assertThat(capturedOut.toString(), containsString("<filename> <XPath> [-i] [--integer] [-n] [--number]"));
        assertThat(capturedOut.toString(), containsString("Xpath might need (Java) escaping of characters such as \\"));
    }

    @Test
    public void fileNotFound() {
        Exception e = assertThrows(FileNotFoundException.class, () -> {
            App.mainExecutor(new String[] { "Non-Existent-File.xml", "//SomeElementType" }, out);
        });

        assertThat(e.getMessage(), containsString("Non-Existent-File.xml"));
    }

    @Test
    public void captureSingleStringValue() throws Exception {
        App.mainExecutor(new String[] { EXAMPLE_XML, "//ContainsSeventeen" }, out);

        assertThat(capturedOut.toString(), is("17" + NL));
    }

    @Test
    public void captureSingleIntegerValue() throws Exception {
        App.mainExecutor(new String[] { EXAMPLE_XML, "//ContainsSeventeen", "--integer" }, out);

        assertThat(capturedOut.toString(), is("17" + NL));
    }

    @Test
    public void captureSingleIntegerValue_FailsIfNotInteger() {
        Exception e = assertThrows(NumberFormatException.class, () -> {
            App.mainExecutor(new String[] { EXAMPLE_XML, "//ContainsOneQuarter", "--integer" }, out);
        });

        assertThat(e.getMessage(), containsString("0.25"));
    }

    @Test
    public void captureSingleNumberValue() throws Exception {
        App.mainExecutor(new String[] { EXAMPLE_XML, "//ContainsOneQuarter", "--number" }, out);

        assertThat(capturedOut.toString(), is("0.25" + NL));
    }

    @Test
    public void captureSingleNumberValue_AcceptsIntegers() throws Exception {
        App.mainExecutor(new String[] { EXAMPLE_XML, "//ContainsSeventeen", "--number" }, out);

        assertThat(capturedOut.toString(), is("17.0" + NL));
    }

    private final ByteArrayOutputStream capturedOut = new ByteArrayOutputStream();
    private final PrintStream out = new PrintStream(capturedOut);

    private static final String NL = String.format("%n");

}
