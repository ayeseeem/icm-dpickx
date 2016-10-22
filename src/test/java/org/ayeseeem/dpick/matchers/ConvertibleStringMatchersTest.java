package org.ayeseeem.dpick.matchers;

import static org.ayeseeem.dpick.matchers.ConvertibleStringMatchers.isNumberOfValue;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConvertibleStringMatchersTest {

    @Test
    public void testIsNumberOfValue() {
        assertThat("12.3", isNumberOfValue(12.3));
    }

    @Test
    public void testIsNumberOfValue_ValueNotMatched() {
        thrown.expect(AssertionError.class);

        assertThat("12.3", isNumberOfValue(45.6));
    }

    @Test
    public void testIsNumberOfValue_ValueNotMatched_MessageDetails() {
        thrown.expectMessage("Expected: Value parsable as a number of value <45.6>");
        thrown.expectMessage("but: was \"12.3\"");

        assertThat("12.3", isNumberOfValue(45.6));
    }

    @Test
    public void testIsNumberOfValue_ValueNotConvertible() {
        thrown.expect(AssertionError.class);

        assertThat("I am not a number", isNumberOfValue(45.6));
    }

    @Test
    public void testIsNumberOfValue_ValueNotConvertible_MessageDetails() {
        thrown.expectMessage("Expected: Value parsable as a number of value <45.6>");
        thrown.expectMessage("but: was \"I am not a number\"");

        assertThat("I am not a number", isNumberOfValue(45.6));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

}
