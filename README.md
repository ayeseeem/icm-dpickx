Data Pickaxe for XML
====================

Library for extracting and checking parts of an XML document


TODO
----

- [ ] Add the XPath (back) into error messages
- [ ] Include failing node number in error messages
- [ ] Throw a library-specific `Error` instead of (or perhaps extension of)
      `AssertionError`
- [ ] Do something with `andDo` - come up with an example usage?
- [ ] Ensure that partial or poor implementations of `NodeSelectionChecker`
      methods fail by default, rather than pass by default
- [ ] Capture required value
- [ ] Capture optional value
- [ ] Capture First or Capture Sole? (=> node count == 1)
- [ ] Capture All for multiple nodes
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
