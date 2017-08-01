package info.example.junit4;

import info.example.HelloWorld;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.*;
import static info.example.junit4.assertions.JUnit5Assertions.assertThrows;


public class HelloWorldTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * The <em>boring</em> good case.
     */
    @Test
    public void verifySayHello() {
        HelloWorld cut = new HelloWorld();
        assertThat(cut.sayHello("world")).isNotNull().isEqualTo("Hello world");
    }

    /**
     * The anti-pattern of how NOT to solve this!!!
     */
    @Test
    public void verifySayHelloErrorManually() {
        HelloWorld cut = new HelloWorld();
        try {
            cut.sayHello(null);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            assertThat(ex).isInstanceOf(IllegalArgumentException.class);
            assertThat(ex).hasMessage("Non-null and non-empty message is required");

        }
        assertThat(cut.sayHello("world")).isNotNull().isEqualTo("Hello world");
    }

    /**
     * Better - but what about the exact exception message (and cause).
     */
    @Test(expected = IllegalArgumentException.class)
    public void verifySayHelloErrorExpected1() {
        HelloWorld cut = new HelloWorld();
        cut.sayHello(null);
    }

    /**
     * Better - but what about the exact exception message (and cause).
     */
    @Test(expected = IllegalArgumentException.class)
    public void verifySayHelloErrorExpected2() {
        HelloWorld cut = new HelloWorld();
        cut.sayHello("wrong");
    }

    /**
     * The preferred way in JUnit 4  - use JUnit 4 rule to get exact exception message (and cause).
     */
    @Test
    public void verifySayHelloErrorExpectedRule1() {
        HelloWorld cut = new HelloWorld();

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Non-null and non-empty message is required");

        cut.sayHello(null);
    }

    /**
     * The preferred way in JUnit 4  - use JUnit 4 rule to get exact exception message (and cause).
     */
    @Test
    public void verifySayHelloErrorExpectedRule2() {
        HelloWorld cut = new HelloWorld();

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Only message 'world' is acceptable");

        cut.sayHello("wrong");
    }

    /**
     * The AssertJ way of exception handling (more generic way).
     */
    @Test
    public void verifySayHelloErrorAssertJGeneric() {
        HelloWorld cut = new HelloWorld();

        // Generic way
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> cut.sayHello("wrong"))
                .withMessage("Only message 'world' is acceptable");
    }

    /**
     * The AssertJ way of exception handling (exception specific way).
     */
    @Test
    public void verifySayHelloErrorAssertJSpecific() {
        HelloWorld cut = new HelloWorld();

        // Specific way
        assertThatIllegalArgumentException()
                .isThrownBy(() -> cut.sayHello("wrong"))
                .withMessage("Only message 'world' is acceptable");
    }

    /**
     * Backport of JUnit 5 way of exception handling.
     */
    @Test
    public void verifySayHelloErrorJUnit5Style() {
        HelloWorld cut = new HelloWorld();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> cut.sayHello("wrong"));
        assertThat(exception.getMessage()).isNotNull().isEqualTo("Only message 'world' is acceptable");
    }

}