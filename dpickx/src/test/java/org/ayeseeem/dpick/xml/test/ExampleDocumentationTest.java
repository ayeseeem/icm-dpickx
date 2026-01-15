package org.ayeseeem.dpick.xml.test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.nullValue;

import java.nio.file.Paths;
import java.util.regex.Pattern;

import org.junit.Test;

public class ExampleDocumentationTest {

    private static final String EXAMPLE_JAVA = "./src/test/java/"
            + (Examples.class.getName()).replace(".", "/")
            + ".java";

    private static final String EXAMPLE_START = "// README Example start";
    private static final String EXAMPLE_END = "// README Example end";

    @Test
    public void verifyExampleCodeFileHasNotMoved() {
        assertThat(EXAMPLE_JAVA,
                is("./src/test/java/org/ayeseeem/dpick/xml/test/Examples.java"));
    }

    @Test
    public void verifyExampleCodeIsIncludedInReadMe() throws Exception {
        Pattern regex = Pattern.compile(
                "(?<=" + EXAMPLE_START + ")(.*)(?=\\s*" + EXAMPLE_END + ")",
                Pattern.DOTALL);

        SnippetExtractor extractor = new SnippetExtractor(regex, UTF_8);

        String contentFromJava = extractor.extractFrom(Paths.get(EXAMPLE_JAVA));
        assertThat(contentFromJava, is(not(nullValue())));
        assertThat(contentFromJava, not(isEmptyString()));

        String contentFromReadme = extractor.extractFrom(Paths.get("../README.md"));
        assertThat(contentFromReadme, is(not(nullValue())));
        assertThat(contentFromReadme, not(isEmptyString()));

        boolean dump = false;
        if (dump) {
            System.err.println(contentFromJava);
            System.err.println(contentFromReadme);
        }
        assertThat(contentFromReadme, is(contentFromJava));
    }

}
