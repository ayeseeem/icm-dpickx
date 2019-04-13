package org.ayeseeem.dpick.matchers;

import static org.ayeseeem.dpick.matchers.ConvertibleStringMatchers.booleanOfValue;
import static org.ayeseeem.dpick.matchers.ConvertibleStringMatchers.numberOfValue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.URI;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConvertibleStringMatchersTest {

    @Test
    public void testNumberOfValue_Matches() {
        Matcher<String> subject = numberOfValue(12.3);
        assertThat(subject.matches("12.3"), is(true));
        assertThat(subject.matches("45.6"), is(false));
        assertThat(subject.matches("Cannot become a number"), is(false));

        assertThat(subject.matches(URI.create("non-string-object")), is(false));
        assertThat(subject.matches(null), is(false));
    }

    @Test
    public void testNumberOfValue_Matches_DoesNotMatchNumbersThatAreNotInStrings() {
        Matcher<String> subject = numberOfValue(12.3);
        assertThat(subject.matches("12.3"), is(true));
        assertThat(subject.matches(12.3), is(false));
    }

    @Test
    public void testNumberOfValue_DescribeTo() {
        Matcher<String> subject = numberOfValue(12.3);
        Description description = new StringDescription().appendText("Initial description - ");

        subject.describeTo(description);

        assertThat(description.toString(), containsString("Initial description - "));
        assertThat(description.toString(), containsString("value parsable as a number"));
        assertThat(description.toString(), containsString("value parsable as a number of value <12.3>"));
        assertThat(description.toString(), is("Initial description - value parsable as a number of value <12.3>"));
    }

    @Test
    public void testNumberOfValue_InUse() {
        assertThat("12.3", numberOfValue(12.3));
    }

    @Test
    public void testNumberOfValue_InUse_ValueNotMatched() {
        thrown.expect(AssertionError.class);

        assertThat("12.3", numberOfValue(45.6));
    }

    @Test
    public void testNumberOfValue_InUse_ValueNotConvertible() {
        thrown.expect(AssertionError.class);

        assertThat("Cannot become a number", numberOfValue(45.6));
    }

    @Test
    public void testNumberOfValue_InUse_WorksWithSyntacticSugar_Is() {
        assertThat("12.3", is(numberOfValue(12.3)));
    }

    @Test
    public void testBooleanOfValue_Matches_True() {
        Matcher<String> subject = booleanOfValue(true);
        assertThat(subject.matches("true"), is(true));
        assertThat(subject.matches("false"), is(false));
        assertThat(subject.matches("Cannot become boolean"), is(false));

        assertThat(subject.matches(URI.create("non-string-object")), is(false));
        assertThat(subject.matches(null), is(false));
    }

    @Test
    public void testBooleanOfValue_Matches_False() {
        Matcher<String> subject = booleanOfValue(false);
        assertThat(subject.matches("true"), is(false));
        assertThat(subject.matches("false"), is(true));
        assertThat(subject.matches("Cannot become boolean"), is(true));

        assertThat(subject.matches(URI.create("non-string-object")), is(false));
        assertThat(subject.matches(null), is(false));
    }

    @Test
    public void testBooleanOfValue_Matches_DoesNotMatchBooleansThatAreNotInStrings() {
        Matcher<String> subject = booleanOfValue(true);
        assertThat(subject.matches("true"), is(true));
        assertThat(subject.matches(true), is(false));
    }

    @Test
    public void testBooleanOfValue_DescribeTo() {
        Matcher<String> subject = booleanOfValue(true);
        Description description = new StringDescription().appendText("Initial description - ");

        subject.describeTo(description);

        assertThat(description.toString(), containsString("Initial description - "));
        assertThat(description.toString(), containsString("value parsable as a boolean"));
        assertThat(description.toString(), containsString("value parsable as a boolean of value <true>"));
        assertThat(description.toString(), is("Initial description - value parsable as a boolean of value <true>"));
    }

    @Test
    public void testBooleanOfValue_InUse() {
        assertThat("true", booleanOfValue(true));
        assertThat("false", booleanOfValue(false));
        assertThat("Cannot become boolean", booleanOfValue(false));
    }

    @Test
    public void testBooleanOfValue_InUse_ValueNotMatched() {
        thrown.expect(AssertionError.class);

        assertThat("false", booleanOfValue(true));
    }

    @Test
    public void testBooleanOfValue_InUse_CannotBeNull() {
        thrown.expect(AssertionError.class);

        assertThat(null, booleanOfValue(true));
    }

    @Test
    public void testBooleanOfValue_InUse_ValueNotConvertible() {
        thrown.expect(AssertionError.class);

        assertThat("Cannot become boolean", booleanOfValue(true));
    }

    @Test
    public void testBooleanOfValue_InUse_WorksWithSyntacticSugar_Is() {
        assertThat("true", is(booleanOfValue(true)));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

}
