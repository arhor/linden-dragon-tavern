package org.arhor.diploma.testutils;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Random;

import static org.arhor.diploma.testutils.ObjectGenerationStrategy.ONLY_PRIMITIVES;
import static org.arhor.diploma.testutils.ParameterGeneratorFactory.getGeneratorForType;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomParameter {

    int length() default -1;

    long min() default Long.MIN_VALUE;

    long max() default Long.MAX_VALUE;

    ObjectGenerationStrategy objectGenerationSTrategy() default ONLY_PRIMITIVES;

    // Random parameter resolver implementation class

    class Resolver implements ParameterResolver {

        public static final Namespace NAMESPACE = Namespace.create(RandomParameter.Resolver.class);

        @Override
        public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            return parameterContext.isAnnotated(RandomParameter.class);
        }

        @Override
        public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            final Class<?> type = parameterContext.getParameter().getType();

            final RandomParameter parameter =
                    parameterContext
                            .findAnnotation(RandomParameter.class)
                            .orElseThrow(IllegalStateException::new);

            return resolveInternal(type, extensionContext, parameter);
        }

        // internal implementation

        private Object resolveInternal(
                Class<?> requiredType,
                ExtensionContext extensionContext,
                RandomParameter parameter) {

            validateAnnotationParams(parameter);

            final var randomizer =
                    extensionContext
                            .getRoot()
                            .getStore(NAMESPACE)
                            .getOrComputeIfAbsent(Random.class);

            return getGeneratorForType(requiredType).generate(randomizer, parameter);
        }

        private void validateAnnotationParams(RandomParameter parameter) {
            final long min = parameter.min();
            final long max = parameter.max();

            if (min > max) {
                throw new IllegalArgumentException(
                        String.format(
                                "Min value must not be greater than max value, but provided min: %d, max: %d",
                                min,
                                max
                        )
                );
            }
        }
    }
}
