package org.ayeseeem.matchers;

import org.ayeseeem.dpick.xml.XpathNodeMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Expressive factory methods for matchers that check {@code String}s can be
 * converted to other types and then match a value.
 * <p>
 * Written to help {@link XpathNodeMatchers#value(Matcher)}, which gets strings
 * from XML.
 */
// TODO: ICM 2019-03-22: Can/should these helpers be extracted into their own artifact or project or into https://github.com/ayeseeem/icm-bits-java?
public class ConvertibleStringMatchers {

    /**
     * Factory method for a matcher that checks if a string can be converted to the
     * specified number. Note that the thing being checked must be a string
     * representation of the number, not the raw number - it will not match the raw
     * number.
     * <p>
     * Usually it is clearer to wrap this with the syntactic sugar of
     * {@link CoreMatchers#is(Matcher)}, for example
     * {@code is(numberOfValue(12.3))}.
     *
     * @param expected
     *            the expected number
     * @return {@code true} if the string can be converted to a number and matches
     *         the expected value; {@code false} if it does not match, <em>or cannot
     *         be converted</em>.
     * @see ConvertibleStringMatchers#numberOfValue(int)
     */
    @Factory
    public static Matcher<String> numberOfValue(double expected) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String possible) {
                try {
                    // TODO: ICM 2019-03-22: Split numberOfValue into numberOf() and numberOfValue()? - check it's a number
                    final double actual = Double.parseDouble(possible);
                    return actual == expected;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("value parsable as a double of value ").appendValue(expected);
            }
        };
    }

    /**
     * Factory method for a matcher that checks if a string can be converted to the
     * specified integer. Note that the thing being checked must be a string
     * representation of the integer, not the raw integer - it will not match the
     * raw integer.
     * <p>
     * Usually it is clearer to wrap this with the syntactic sugar of
     * {@link CoreMatchers#is(Matcher)}, for example {@code is(numberOfValue(123))}.
     *
     * @param expected
     *            the expected integer
     * @return {@code true} if the string can be converted to an integer and matches
     *         the expected value; {@code false} if it does not match, <em>or cannot
     *         be converted</em>.
     * @see ConvertibleStringMatchers#numberOfValue(double)
     */
    public static Matcher<String> numberOfValue(int expected) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String possible) {
                try {
                    // TODO: ICM 2019-03-22: Split numberOfValue into numberOf() and numberOfValue()? - check it's a number
                    final int actual = Integer.parseInt(possible);
                    return actual == expected;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("value parsable as an integer of value ").appendValue(expected);
            }
        };
    }

    /**
     * Factory method for a matcher that checks if a string can be converted to the
     * specified boolean. Note that the thing being checked must be a string
     * representation of the boolean, not the raw boolean - it will not match the
     * raw boolean. Note also that the conversion is case-insensitive, as specified
     * by {@link Boolean#parseBoolean(String)}.
     * <p>
     * Usually it is clearer to wrap this with the syntactic sugar of
     * {@link CoreMatchers#is(Matcher)}, for example
     * {@code is(booleanOfValue(true))}.
     *
     * @param expected
     *            the expected boolean
     * @return {@code true} if the string can be converted to a boolean and matches
     *         the expected value; {@code false} if it does not match, <em>or cannot
     *         be converted</em>.
     */
    @Factory
    public static Matcher<String> booleanOfValue(boolean expected) {
        return new TypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafely(String possible) {
                // TODO: ICM 2019-03-22: Split booleanOfValue into booleanOf() and booleanOfValue()? - check it's a boolean
                final boolean actual = Boolean.parseBoolean(possible);
                return actual == expected;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("value parsable as a boolean of value ").appendValue(expected);
            }
        };
    }

}
