package org.ayeseeem.dpick.matchers;

import org.ayeseeem.dpick.xml.XpathNodeMatchers;
import org.hamcrest.CoreMatchers;
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
     * <p>
     * Usually it is clearer to wrap this with the syntactic sugar of
     * {@link CoreMatchers#is(Matcher)}, for example
     * {@code is(numberOfValue(12.3))}
     *
     * @param expected
     *            the expected number
     * @return {@code true} if it can be converted to a number and matches the
     *         value; {@code false} if it does not match, or cannot be converted
     */
    @Factory
    public static Matcher<String> numberOfValue(double expected) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String possible) {
                try {
                    final double actual = Double.parseDouble(possible);
                    return actual == expected;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("value parsable as a number of value ").appendValue(expected);
            }
        };
    }

    /**
     * Factory method for a matcher that checks if a string can be converted to
     * the specified boolean
     * <p>
     * Usually it is clearer to wrap this with the syntactic sugar of
     * {@link CoreMatchers#is(Matcher)}, for example
     * {@code is(booleanOfValue(true))}
     *
     * @param expected
     *            the expected boolean
     * @return {@code true} if it can be converted to a boolean and matches the
     *         value; {@code false} if it does not match, or cannot be converted
     */
    @Factory
    public static Matcher<String> booleanOfValue(boolean expected) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String possible) {
                try {
                    final boolean actual = Boolean.parseBoolean(possible);
                    return actual == expected;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("value parsable as a boolean of value ").appendValue(expected);
            }
        };
    }

}
