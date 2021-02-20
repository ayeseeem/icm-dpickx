Data Pickaxe for XML
====================

[![Build Status](https://travis-ci.org/ayeseeem/icm-dpickx.svg?branch=master)](https://travis-ci.org/ayeseeem/icm-dpickx)

Library and tool for extracting and checking parts of an XML document.


Quick Start
-----------

`dpickx` lets you use XPaths to select nodes in an XML document and then
do the following:

- Express your expectations/make assertions about the selected nodes
- Process the selected nodes
- Capture the values of selected nodes

The following snippets give an idea of what you can do.

For an XML document like this:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<RootElement>
  <SomethingUnique/>
  <Repeated/>
  <Repeated/>
  <ElementWithSizeAttribute size="15"/>
  <ContainsSeventeen>17</ContainsSeventeen>
  <ContainsAttributeWithEighteen attrOf18="18">blah blah</ContainsAttributeWithEighteen>
  <Duplicate>123</Duplicate>
  <Duplicate>123</Duplicate>
  <DuplicateEleDiffContent>123</DuplicateEleDiffContent>
  <DuplicateEleDiffContent>456</DuplicateEleDiffContent>
  <AlwaysTrue>true</AlwaysTrue>
</RootElement>
```

Convert it to a DOM, then you can make assertions like this:

```java
Node eg = topLevelDocumentOrSomeNodeFromXml();
XmlDocumentChecker.check(eg)
        .andExpect(xpath("/RootElement/SomethingUnique").exists())
        .andExpect(xpath("//NeverExisting").doesNotExist())
        .andExpect(xpath("//Repeated").exists())
        .andExpect(xpath("//Repeated").nodeCount(2))
        .andExpect(xpath("/NeverExistingTopLevel").doesNotExist())
        .andExpect(xpath("//ContainsSeventeen").value(is("17")))
        .andExpect(xpath("//ContainsSeventeen").value(containsString("7")))
        .andExpect(xpath("//ContainsSeventeen").value(startsWith("1")))
        .andExpect(xpath("//ContainsSeventeen").value(is(numberOfValue(17.0))))
        .andExpect(xpath("//ContainsSeventeen").value(is(numberOfValue(17))))
        .andExpect(xpath("//ElementWithSizeAttribute/@size").exists())
        .andExpect(xpath("//ElementWithSizeAttribute/@size").value(is("15")))
        ;
```

And you can select and process nodes like this:

```java
List<Node> fakeConsumer = new ArrayList<>();
XmlDocumentChecker.check(eg)
        .andExpect(xpath("//Repeated").nodeCount(2))
        .andDo(xpath("//Repeated").processEach(node -> fakeConsumer.add(node)))
        .andDo(xpath("//Repeated").processEach(node -> {
            // do something complicated with node
        }))
        ;
```

And you can capture a node's value like this:

```java
XmlDocumentChecker checker = new XmlDocumentChecker(eg);
String drivingAge = checker.captureSoleRequired(xpath("//ContainsSeventeen"));

// do something with captured value...
assertThat(drivingAge, is("17"));
```

You can also capture optional values, like this:

```java
String drivingAge = checker.captureSoleOptional(xpath("//ContainsSeventeen")).orElse("21");
assertThat(drivingAge, is("17"));

String votingAge = checker.captureSoleOptional(xpath("//DoesContainVotingAge")).orElse("18");
assertThat(votingAge, is("18"));
```

To see more examples, see [`Examples.java`](src/test/java/org/ayeseeem/dpick/xml/test/Examples.java "Examples")


`dpickx-app` has been begun as a command-line tool to let you apply `dpickx` to
files.
At the moment, it just applies `captureSoleRequired` and outputs it to `stdout`.
You can build and run it like this:

```shell
mvn clean package && pushd dpickx-app && java -jar target/dpickx-app-0.10.0-SNAPSHOT-jar-with-dependencies.jar example.xml ///ContainsSeventeen&& popd
```


Applying XPaths
---------------

XPaths are applied to the node passed in to the `XmlDocumentChecker.check()`
method in a conventional way.
That is, relative XPaths are relative to the node passed in, but absolute paths
still apply to the whole document.
See the [`Examples.java`](src/test/java/org/ayeseeem/dpick/xml/test/Examples.java)
for more details.


TODO
----

### Library `dpickx`

- [x] Rearrange the main `dpickx` lib code into a sub-folder called `dpickx` (or
      `dpickx-lib`?)
- [x] Then make a parent/build-all POM at top level, for use by `-app` (below).
- [ ] Fix [Issue 1](https://github.com/ayeseeem/icm-dpickx/issues/1#issue-733753076)
- [x] Rename artifact to be `dpickx`, not `icm-dpickx`, as the `icm-` is
      handled by the `groupId`.
- [ ] Include failing node number in error messages?
- [x] Capture optional value
  - Capture Sole Optional added, to correspond with Capture Sole Required.
    Any other methods added should consider an Optional alternative.
- [ ] Capture First value (c.f. Capture Sole)? (=> node count >= 1)
- [ ] Capture All for multiple nodes
- [ ] Templatize the capture methods to return specific types, not just
      `String`s
- [ ] Consider if the more complicated ways of doing capture are still needed:
      can the whole mechanism be simplified?
- [ ] Throw a library-specific `Error` instead of (or perhaps extension of)
      `AssertionError`?
- [ ] Ensure that partial or poor implementations of `NodeSelectionChecker`
      methods fail by default, rather than pass by default
- [ ] Consider
      [XMLUnit](http://www.xmlunit.org/) - use to implement some of the internal
      XPath evaluations etc, and/or document using it explicitly in parallel
      with `dpickx` to check the XML is valid etc.
- [x] There was an intention to be able to apply checks to just part of a
      document tree. Check if this works, and check/change examples and tests
      to show this.
      Relative paths apply relative to the node, absolute paths still refer to
      the whole document.
- [ ] Support XML Namespaces. The original code had some capabilities to handle
      namespaces: does the current code? There is the unused, helper class
      `DomHelper`, which shows an approach that might work. Compare it to how
      `XpathHelper` works.
- [x] Original code had helper methods to create XML documents. Consider
      bringing some in here.
- [ ] Improve the documentation of the added `DomHelper` and `XmlUnmarshaller`,
      as they are unused at the moment. This README and/or their Javadocs
      should explain what they are for and why they've been added - see
      preceding and following TODOs about namespaces and a command-line tool
- [ ] Investigate using Hamcrest
      [HasXPath](http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/xml/HasXPath.html)
- [ ] Investigate using Hamcrest
      [TypeSafeDiagnosingMatcher](http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/TypeSafeDiagnosingMatcher.html)


### Tool `dpickx-app`

- [x] **Create a tool!** - Make a command-line utility that can extract a value
      from an XML file (using a given XPath).
- [x] Use the added helper class `XmlUnmarshaller` to convert the XML file into
      a DOM.
- [x] Rearrange code to be in a folder called `dpickx-app`, not `icm-dpickx-...`
- [x] Rename artifact to be `dpickx-app`, not `icm-dpickx-app`, as the `icm-` is
      handled by the `groupId`.
- [x] Update this README to mention (and explain how to use) the app.
- [x] Spring Boot has only been used to "simplify" creating a runnable `.jar`
      (and to specify a set of versions of packages).
      This makes a 12 MB jar, which is excessive. Investigate a cleaner way of
      doing this, with a smaller (set of) jar(s).
      Using Apache Maven Assembly Plugin, reduced jar size to 101 kB.
- [x] De-duplicate POM settings by depending on a Parent POM, once there is one.
- [ ] Replace the test file included with the app - `example.xml` - with a way
      of generating it from the code in `XmlExampleFixture`.
      The file was originally created by hand, capturing it from the console
      output when the library tests were run.
- [ ] Add more features: flags to control what to do, separate logging


Origin
------

This code was extracted from a personal project with a view to one day
publishing it as a separate module. It sat untouched for years, but perhaps
now is the time to do that.

It was originally inspired by some of the XPath matching in
[Spring MVC Test](https://github.com/spring-projects/spring-framework/tree/master/spring-test),
but I needed something more general for XML (and JSON), and I wanted
something that would eventually allow me to capture required or optional
values from the node too.


Coding Standard
---------------

Currently uses the "Eclipse [Built-in]" settings in Eclipse/Spring STS, for
Java > Code Style's Clean Up and Formatter.

- **Except** uses 4 spaces for indentation, not tab.
- This includes maximum line lengths as follows
  - 120 for code
  - 80 for comments, but
    "from comment's starting position".
    The starting position bit is nice because it means that comment blocks
    don't need to be reformatted when the commented code's indentation level
    changes, for example when it is refactored to move it into or out of nested
    classes.

Don't (usually) modify method parameters.
But don't (usually) use `final` on (every) method parameter, to prevent
it - we think it makes the code unhelpfully verbose.
Instead, turn on an IDE rule or code linter to warn on reassigned parameters.
Only use `final` on method parameters in special cases - for example if the
method is so long that it is hard to see at a glance that the parameters are
not changed (although, avoid such long methods!). Or where some of the
parameters _are_ reassigned - use `final` to mark the ones that are not.


### Rules

- Simplify lambdas:
  - Remove `{ }` from around single liners
  - Prefer method references where possible
- Don't use `public` on interface methods
  [JLS Method Declarations](https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.4)
  says this (our emphasis):
    > Every method declaration in the body of an interface is implicitly
    > public (§6.6). It is permitted, but **discouraged as a matter of style**,
    > to redundantly specify the public modifier for a method declaration in
    > an interface.


### Coding Standard TODOs

- [ ] Look for `final` variables that can be removed by inlining the variable
