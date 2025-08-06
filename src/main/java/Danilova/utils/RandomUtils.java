package Danilova.utils;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomUtils {
    private RandomUtils() {
        // a private constructor so that the class cannot be instantiated
    }

    /**
     * Returns a random integer from min to max inclusive.
     *
     * @param min lower bound (inclusive)
     * @param max upper bound (inclusive), must be >= min
     * @return a random number in the range [min, max]
     * @throws IllegalArgumentException if max < min
     */
    public static int randomIntInclusive(int min, int max) {
        if (max < min) {
            throw new IllegalArgumentException("max must be not less then min is");
        }
        // nextInt(bound) return [0, bound), so plus +1
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
