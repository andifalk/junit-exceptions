package info.example.junit4.assertions;

import junit.framework.AssertionFailedError;

import java.util.Objects;

/**
 * Just a quick simple solution to backport JUnit 5 style of exception verification.
 * Code inspired by JUnit 5 and AssertJ.
 */
public final class JUnit5Assertions {

    /**
     * <em>Asserts</em> that execution of the supplied {@code executable} throws
     * an exception of the {@code expectedType} and returns the exception.
     *
     * <p>If no exception is thrown, or if an exception of a different type is thrown,
     * this method will fail.
     *
     * <p>If you do not want to perform additional checks on the exception instance,
     * simply ignore the return value.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T assertThrows(Class<T> expectedType, ThrowingCallable throwingCallable) {

        try {
            throwingCallable.call();
        }
        catch (Throwable actualException) {
            if (expectedType.isInstance(actualException)) {
                return (T) actualException;
            }
            else {
                String message = format(expectedType, actualException.getClass());
                throw new AssertionFailedError(message);
            }
        }

        throw new AssertionFailedError(String.format("Expected %s to be thrown, but nothing was thrown.", getCanonicalName(expectedType)));
    }

    private static String getCanonicalName(Class<?> clazz) {
        try {
            String canonicalName = clazz.getCanonicalName();
            return (canonicalName != null ? canonicalName : clazz.getName());
        }
        catch (Throwable t) {
            return clazz.getName();
        }
    }

    private static String format(Object expected, Object actual) {
        return "Unexpected exception type thrown  ==> " + formatValues(expected, actual);
    }

    private static String formatValues(Object expected, Object actual) {
        String expectedString = Objects.toString(expected);
        String actualString = Objects.toString(actual);
        if (expectedString.equals(actualString)) {
            return String.format("expected: %s but was: %s", formatClassAndValue(expected, expectedString),
                    formatClassAndValue(actual, actualString));
        }
        else {
            return String.format("expected: <%s> but was: <%s>", expectedString, actualString);
        }
    }

    private static String formatClassAndValue(Object value, String valueString) {
        String classAndHash = value.getClass().getName() + value.hashCode();
        return (value instanceof Class ? "<" + classAndHash + ">" : classAndHash + "<" + valueString + ">");
    }

    public interface ThrowingCallable {
        void call() throws Throwable;
    }

}
