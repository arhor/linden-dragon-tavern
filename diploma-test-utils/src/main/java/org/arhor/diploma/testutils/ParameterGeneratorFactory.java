package org.arhor.diploma.testutils;

import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public final class ParameterGeneratorFactory {

    static final Logger log = LoggerFactory.getLogger(ParameterGeneratorFactory.class);

    private ParameterGeneratorFactory() {}

    public static ParameterGenerator getGeneratorForType(Class<?> type) {
        for (var generator : PrimitiveGenerator.values()) {
            if (generator.supportsType(type)) {
                return generator;
            }
        }

        // todo: add proper logging as SLF4J
        log.debug(() -> "[" + type + "] generator is not found, using Object generator as a fallback");

        return new ObjectGenerator(type);
    }
}
