package org.arhor.diploma.testutils;

import java.util.Random;

@FunctionalInterface
interface ParameterGenerator {
    Object generate(Random randomizer, RandomParameter parameter);
}
