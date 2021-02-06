package org.arhor.diploma.testutils;

import org.junit.jupiter.api.extension.ParameterResolutionException;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

import static org.arhor.diploma.testutils.ParameterGeneratorFactory.getGeneratorForType;

final class ObjectGenerator implements ParameterGenerator {

    private static final MethodType NO_ARGS_CONSTRUCTOR = MethodType.methodType(void.class);

    private final Class<?> type;
    private final MethodHandles.Lookup lookup;

    ObjectGenerator(final Class<?> type) {
        this.type = type;
        try {
            this.lookup = MethodHandles.privateLookupIn(type, MethodHandles.lookup());
        } catch (IllegalAccessException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public Object generate(Random randomizer, RandomParameter parameter) {
        final Object obj = instantiateObject();

        writeProperties(obj, randomizer, parameter);

        return obj;
    }

    private Object instantiateObject() {
        final MethodHandle constructor;

        try {
            constructor = lookup.findConstructor(type, NO_ARGS_CONSTRUCTOR);
        } catch (NoSuchMethodException e) {
            throw new ParameterResolutionException("Missing no-arg constructor", e);
        } catch (IllegalAccessException e) {
            throw new ParameterResolutionException("Could not access no-arg constructor", e);
        }

        final Object instance;

        try {
            instance = constructor.invoke();
        } catch (Throwable throwable) {
            throw new ParameterResolutionException("Could not instantiate [" + type + "]", throwable);
        }

        return instance;
    }

    private void writeProperties(final Object obj, final Random randomizer, final RandomParameter parameter) {
        for (Field declaredField : type.getDeclaredFields()) {
            if (!isAccessModifiersAreValid(declaredField)) {
                continue;
            }
            final Class<?> fieldType = declaredField.getType();

            if (isFieldCanBeGenerated(fieldType, parameter.objectGenerationSTrategy())) {
                final var fieldValue = getGeneratorForType(fieldType).generate(randomizer, parameter);
                try {
                    final var setter = lookup.unreflectSetter(declaredField);
                    setter.invoke(obj, fieldValue);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }

    private boolean isAccessModifiersAreValid(Field declaredField) {
        final int mod = declaredField.getModifiers();
        return !(Modifier.isFinal(mod) || Modifier.isStatic(mod));
    }

    private static boolean isFieldCanBeGenerated(Class<?> fieldType, ObjectGenerationStrategy strategy) {
        return fieldType.isPrimitive()
                || String.class.equals(fieldType)
                || ObjectGenerationStrategy.NESTED_OBJECTS.equals(strategy);
    }
}
