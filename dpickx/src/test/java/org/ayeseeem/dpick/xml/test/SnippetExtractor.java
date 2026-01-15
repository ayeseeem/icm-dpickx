package org.ayeseeem.dpick.xml.test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Extract snippets from files.
 */
class SnippetExtractor {

    private final Pattern regex;
    private final Charset charset;

    SnippetExtractor(Pattern regex, Charset charset) {
        this.regex = regex;
        this.charset = charset;
    }

    String extractFrom(Path source) throws IOException {
        try (Scanner scanner = new Scanner(source, charset.name())) {
            return scanner.findWithinHorizon(regex, 0);
        }
    }

}
