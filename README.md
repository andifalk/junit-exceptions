# JUnit Exception Verification

This github repository contains short code samples showing how (not) to
verify exceptions and exception messages in unit tests using
for [JUnit 4](http://junit.org/junit4), [JUnit 5](http://junit.org/junit5) and [AssertJ](https://joel-costigliola.github.io/assertj).

## Class under test

To keep things easy the production code class used in the tests is just a simple class
implemented in _info.example.HelloWorld_. 
In this class the classical hello world operation _String sayHello(String message)_ is implemented.

Different from usual implementations this operation only accepts the message _'world'_ as valid parameter value.
A _null_ value and a all values different from _'world'_ will throw an _IllegalArgumentException_ with two different 
specific messages. 

## JUnit 4

The test class _info.example.junit4.HelloWorldTest_ shows different ways to verify exceptions in JUnit 4 style using
following mechanisms:

*  manually (the _anti-pattern_)
*  using @Test annotation with expected param
*  JUnit rule
*  AssertJ assertions
*  Back ported JUnit 5 assertion  

## JUnit 5

The test class _info.example.junit5.HelloWorldTest_ shows different ways to verify exceptions in JUnit 5 style using
following mechanisms:

*  manually (the _anti-pattern_)
*  AssertJ assertions
*  JUnit 5 assertion  
