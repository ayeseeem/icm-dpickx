Data Pickaxe for XML
====================

[![Build Status](https://travis-ci.org/ayeseeem/icm-dpickx.svg?branch=master)](https://travis-ci.org/ayeseeem/icm-dpickx)

Library for extracting and checking parts of an XML document


Quick Start
-----------

`icm-dpickx` lets you use XPaths to select nodes in an XML document and then
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
        .andExpect(xpath("//ContainsSeventeen").value(StringStartsWith.startsWith("1")))
        .andExpect(xpath("//ContainsSeventeen").value(is(numberOfValue(17.0))))
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

To see more examples, see [Examples.java](https://github.com/ayeseeem/icm-dpickx/blob/master/src/test/java/org/ayeseeem/dpick/xml/Examples.java "Examples")


TODO
----

- [ ] Include failing node number in error messages?
- [ ] Capture optional value
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
- [ ] There was an intention to be able to apply checks to just part of a
      document tree. Check if this works, and check/change examples and tests
      to show this
- [ ] Support XML Namespaces. The original code had some capabilities to handle
      namespaces: does the current code?
- [ ] Original code had helper methods to create XML documents. Consider
      bringing some in here. 

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
