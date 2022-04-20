package com.github.arhor.linden.dragon.tavern.testutils;

import java.util.Random;

@FunctionalInterface
interface ParameterGenerator {
    Object generate(Random randomizer, RandomParameter parameter);
}
