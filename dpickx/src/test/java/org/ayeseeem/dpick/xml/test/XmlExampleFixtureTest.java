package org.ayeseeem.dpick.xml.test;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.ayeseeem.dpick.util.xml.DomDump;
import org.junit.Test;
import org.w3c.dom.Document;

public class XmlExampleFixtureTest {

    private static final String EXAMPLE_XML = "../dpickx-app/example.xml";

    @Test
    public void verifyExampleFileHasNotChanged() throws Exception {
        createExampleFile();

        List<String> latest = readAllLines(EXAMPLE_XML);
        List<String> reference = readAllLines("../dpickx-app/example--goldbrick.xml");

        // Better diagnostic than simple list compare
        assertThat(latest, contains(reference.toArray()));

        assertThat(latest, is(reference));
    }

    @Test
    public void verifyExampleFileIsIncludedInReadMe() throws Exception {
        List<String> readMe = readAllLines("../README.md");
        List<String> example = readAllLines(EXAMPLE_XML);

        // TODO: ICM 2026-01-08: Make a helper like my ContainedItem - ContainedCollection? .containsSubList()?
        assertThat("Example should be included in README",
                Collections.indexOfSubList(readMe, example), is(not(-1)));
    }

    // TODO: ICM 2026-01-08: Verify example of *usage* of the example file is in README

    void createExampleFile() throws FileNotFoundException {
        Document doc = XmlExampleFixture.createExampleDocument();
        DomDump.safeDumpToConsole(doc);
        OutputStream sink = new FileOutputStream(EXAMPLE_XML);
        DomDump.safeDump(doc, sink);
    }

    private List<String> readAllLines(String sourcePath) throws IOException {
        return readAllLines(Paths.get(sourcePath));
    }

    private List<String> readAllLines(Path source) throws IOException {
        try (Stream<String> data = Files.lines(source)) {
            List<String> lines = data.collect(toList());
            return lines;
        }
    }

    // TODO: ICM 2026-01-08: For Java after Java 8

//    private List<String> readAllLines(String sourcePath) throws IOException {
//        return readAllLines(Path.of(sourcePath));
//    }
//
//    private List<String> readAllLines(Path source) throws IOException {
//        return Files.readAllLines(source);
//    }

}
