package org.arhor.diploma.commons;

import java.util.Random;

public final class TestUtils {

    private TestUtils() {}

    public static int randomInt(int bound) {
        return new Random().nextInt(bound);
    }
}
