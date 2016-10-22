package org.ayeseeem.dpick.matchers;

import static org.ayeseeem.dpick.matchers.ConvertibleStringMatchers.numberOfValue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConvertibleStringMatchersTest {

    @Test
    public void testNumberOfValue() {
        assertThat("12.3", numberOfValue(12.3));
    }

    @Test
    public void testNumberOfValue_ValueNotMatched() {
        thrown.expect(AssertionError.class);

        assertThat("12.3", numberOfValue(45.6));
    }

    @Test
    public void testNumberOfValue_ValueNotMatched_MessageDetails() {
        thrown.expectMessage("Expected: value parsable as a number of value <45.6>");
        thrown.expectMessage("but: was \"12.3\"");

        assertThat("12.3", numberOfValue(45.6));
    }

    @Test
    public void testNumberOfValue_ValueNotConvertible() {
        thrown.expect(AssertionError.class);

        assertThat("I am not a number", numberOfValue(45.6));
    }

    @Test
    public void testNumberOfValue_ValueNotConvertible_MessageDetails() {
        thrown.expectMessage("Expected: value parsable as a number of value <45.6>");
        thrown.expectMessage("but: was \"I am not a number\"");

        assertThat("I am not a number", numberOfValue(45.6));
    }

    @Test
    public void testNumberOfValue_WorksWithSyntacticSugar_Is() {
        assertThat("12.3", is(numberOfValue(12.3)));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

}
