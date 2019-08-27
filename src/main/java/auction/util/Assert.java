package auction.util;

/**
 * Utility class containing methods to validate objects
 *
 * @author Alexey Smirnov
 */
public final class Assert {
    /**
     * Checks if argument is non-null
     *
     * @param o       object to check
     * @param message error message
     */
    public static void argumentNotNull(Object o, String message) {
        if (o == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if class member variable is non-null
     *
     * @param o       object to check
     * @param message error message
     */
    public static void stateNotNull(Object o, String message) {
        if (o == null) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Checks if argument is positive
     *
     * @param number  number to check
     * @param message error message
     */
    public static void argumentIsPositive(int number, String message) {
        if (number <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if argument is non-negative
     *
     * @param number  number to check
     * @param message error message
     */
    public static void argumentIsNonNegative(int number, String message) {
        if (number < 0) {
            throw new IllegalArgumentException(message);
        }
    }

    private Assert() {
        //not allowed to be instantiated
    }
}
