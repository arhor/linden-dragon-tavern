package org.arhor.diploma.testutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ParameterGeneratorFactory {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final Map<Class<?>, ParameterGenerator> GENERATOR_CACHE = new ConcurrentHashMap<>();

    private ParameterGeneratorFactory() {}

    public static ParameterGenerator getGeneratorForType(Class<?> type) {
        for (var generator : PrimitiveGenerator.values()) {
            if (generator.supportsType(type)) {
                log.debug("Primitive [{}] is used to generate value", type);
                return generator;
            }
        }

        if (type.isArray()) {
            Class<?> componentType = type.getComponentType();
            if (log.isDebugEnabled()) {
                log.debug("Array [{}] is used to generate value", shortenPackagesPrefix(componentType));
            }
            return GENERATOR_CACHE.computeIfAbsent(type, arrayType -> new ArrayGenerator<>(componentType));
        }

        log.debug("[{}] generator is not found, using Object generator as a fallback", type);

        return GENERATOR_CACHE.computeIfAbsent(type, ObjectGenerator::new);
    }

    private static String shortenPackagesPrefix(Class<?> objClass) {
        String[] names = objClass.getName().split("\\.");
        for (int i = 0, namesLength = names.length; i < namesLength; i++) {
            if (i != namesLength - 1) {
                names[i] = names[i].substring(0, 1);
            }
        }
        return String.join(".", names);
    }
}
