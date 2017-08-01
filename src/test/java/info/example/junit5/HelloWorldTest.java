package info.example.junit5;

import info.example.HelloWorld;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HelloWorldTest {

    /**
     * The <em>boring</em> good case.
     */
    @DisplayName("Just say hello")
    @Test
    void verifySayHello() {
        HelloWorld cut = new HelloWorld();
        assertThat(cut.sayHello("world")).isNotNull().isEqualTo("Hello world");
    }

    /**
     * The anti-pattern of how NOT to solve this!!!
     */
    @DisplayName("Should not be done this way")
    @Test
    void verifySayHelloErrorManually() {
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
     * Both JUnit 4 ways not supported any more: No expected param and no JUnit rule too.
     */
    // @Test(expected = IllegalArgumentException.class)
    void verifySayHelloErrorExpected() {
    }

    /**
     * The AssertJ way of exception handling (more generic way).
     */
    @DisplayName("AssertJ way of exception handling (1)")
    @Test
    void verifySayHelloErrorAssertJGeneric() {
        HelloWorld cut = new HelloWorld();

        // Generic way
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> cut.sayHello("wrong"))
                .withMessage("Only message 'world' is acceptable");

    }

    /**
     * The AssertJ way of exception handling (exception specific way).
     */
    @DisplayName("AssertJ way of exception handling (2)")
    @Test
    void verifySayHelloErrorAssertJSpecific() {
        HelloWorld cut = new HelloWorld();

        // Specific way
        assertThatIllegalArgumentException()
                .isThrownBy(() -> cut.sayHello("wrong"))
                .withMessage("Only message 'world' is acceptable");

    }

    /**
     * The preferred way in JUnit 5 to verify exception with exact exception message (and cause).
     */
    @DisplayName("JUnit5 way of exception handling")
    @Test
    void verifySayHelloErrorJUnit5() {
        HelloWorld cut = new HelloWorld();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> cut.sayHello("wrong"));
        assertThat(exception.getMessage()).isNotNull().isEqualTo("Only message 'world' is acceptable");
    }

}