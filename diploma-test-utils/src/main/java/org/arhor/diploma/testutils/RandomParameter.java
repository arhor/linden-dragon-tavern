package org.arhor.diploma.testutils;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomParameter {

    int length() default -1;

    long min() default Long.MIN_VALUE;

    long max() default Long.MAX_VALUE;

    class Resolver implements ParameterResolver {

        public static final Namespace NAMESPACE = Namespace.create(RandomParameter.Resolver.class);

        @Override
        public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            return parameterContext.isAnnotated(RandomParameter.class);
        }

        @Override
        public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
            return resolveInternal(
                    parameterContext.getParameter().getType(),
                    extensionContext,
                    parameterContext.findAnnotation(RandomParameter.class).orElseThrow(IllegalStateException::new)
            );
        }

        private Object resolveInternal(
                Class<?> requiredType,
                ExtensionContext extensionContext,
                RandomParameter parameterAnnotation) {

            validateAnnotationParams(parameterAnnotation);

            final var generator = Generator
                    .fromType(requiredType)
                    .orElseThrow(() -> new ParameterResolutionException(
                            String.format("No random generator implemented for %s", requiredType)
                    ));






            final var randomizer = extensionContext
                    .getRoot()
                    .getStore(NAMESPACE)
                    .getOrComputeIfAbsent(Random.class);

            return generator.generate(randomizer, parameterAnnotation);
        }

        private void validateAnnotationParams(RandomParameter annotation) {
            final long min = annotation.min();
            final long max = annotation.max();

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

        private enum Generator {

            BYTES(byte.class, Byte.class) {
                @Override
                public Object generate(Random randomizer, RandomParameter randomParameter) {
                    var byteHolder = new byte[1];
                    randomizer.nextBytes(byteHolder);
                    return byteHolder[0];
                }
            },

            SHORTS(short.class, Short.class) {
                @Override
                public Object generate(Random randomizer, RandomParameter randomParameter) {
                    return (short) randomizer.nextInt();
                }
            },

            INTEGERS(int.class, Integer.class) {
                @Override
                public Object generate(Random randomizer, RandomParameter randomParameter) {
                    final long min = randomParameter.min();
                    final long max = randomParameter.max();

                    if ((min <= Integer.MIN_VALUE) && (max >= Integer.MAX_VALUE)) {
                        return randomizer.nextInt();
                    }

                    return randomizer
                            .ints()
                            .filter(value -> value >= min && value <= max)
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException(
                                    String.format(
                                            "Cannot generate random integer within the bound [%d, %d]",
                                            min,
                                            max
                                    )
                            ));
                }
            },

            LONGS(long.class, Long.class) {
                @Override
                public Object generate(Random randomizer, RandomParameter randomParameter) {
                    return randomizer.nextLong();
                }
            },

            FLOATS(float.class, Float.class) {
                @Override
                public Object generate(Random randomizer, RandomParameter randomParameter) {
                    return randomizer.nextFloat();
                }
            },

            DOUBLES(double.class, Double.class) {
                @Override
                public Object generate(Random randomizer, RandomParameter randomParameter) {
                    return randomizer.nextDouble();
                }
            },

            BOOLEANS(boolean.class, Boolean.class) {
                @Override
                public Object generate(Random randomizer, RandomParameter randomParameter) {
                    return randomizer.nextBoolean();
                }
            },

            CHARACTER_SEQUENCES(String.class) {
                @Override
                public Object generate(Random randomizer, RandomParameter randomParameter) {
                    return (randomParameter.length() > 0)
                            ? RandomString.make(randomParameter.length())
                            : RandomString.make();
                }
            },

            ;

            private final Set<Class<?>> supportedTypes;

            public abstract Object generate(Random randomizer, RandomParameter randomParameter);

            Generator(Class<?>... types) {
                this.supportedTypes = Set.of(types);
            }

            public static Optional<Generator> fromType(Class<?> type) {
                for (var generator : values()) {
                    if (generator.supportedTypes.contains(type)) {
                        return Optional.of(generator);
                    }
                }
                return Optional.empty();
            }
        }
    }
}
