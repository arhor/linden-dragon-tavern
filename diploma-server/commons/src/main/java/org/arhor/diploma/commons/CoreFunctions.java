package org.arhor.diploma.commons;

import java.util.function.Supplier;

public final class CoreFunctions {

    private CoreFunctions() {
        throw new IllegalStateException("Must not be instantiated!");
    }

    /**
     * Executes provided functions one-by-one until first non-null result achieved.
     *
     * @param sources functions to execute
     * @param <T> expected return type
     * @return first non-null result
     */
    @SafeVarargs
    public static <T> T coalesce(Supplier<T>... sources) {
        T result = null;
        for (Supplier<T> source : sources) {
            if ((result = source.get()) != null) {
                break;
            }
        }
        return result;
    }
}
