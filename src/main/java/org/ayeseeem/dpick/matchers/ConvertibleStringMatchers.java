package org.ayeseeem.dpick.matchers;

import org.ayeseeem.dpick.xml.XpathNodeMatchers;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Expressive factory methods for matchers check {@code String}s can be
 * converted to other types and then match a value.
 * <p>
 * Written to help
 * {@link XpathNodeMatchers#value(Matcher)}, which gets
 * strings from XML.
 *
 * @author ayeseeem@gmail.com
 */
public class ConvertibleStringMatchers {

    /**
     * Factory method for a matcher that checks if a string can be converted to
     * the specified number
     *
     * @param expected
     *            the expected number
     * @return {@code true} if it can be converted to a number and matches the
     *         value; {@code false} if it does not match, or cannot be converted
     */
    // TODO: ICM 2016-10-22: Convert to something like is(number().ofValue())?
    @Factory
    public static Matcher<String> isNumberOfValue(final double expected) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(final String s) {
                try {
                    final double actual = Double.parseDouble(s);
                    return actual == expected;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("Value parsable as a number of value ").appendValue(expected);
            }
        };
    }

}
