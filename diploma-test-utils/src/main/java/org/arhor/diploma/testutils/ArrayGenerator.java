package org.arhor.diploma.testutils;

import java.util.Random;

import static org.arhor.diploma.testutils.ParameterGeneratorFactory.getGeneratorForType;

@SuppressWarnings("unchecked")
final class ArrayGenerator<T> implements ParameterGenerator {

    private static final Object[] EMPTY_ARRAY = new Object[0];

    private final Class<T> arrayType;

    ArrayGenerator(Class<T> arrayType) {
        this.arrayType = arrayType;
    }

    @Override
    public T[] generate(Random randomizer, RandomParameter parameter) {
        final T[] array = allocateArray(parameter);

        if (array != EMPTY_ARRAY) {
            var generator = getGeneratorForType(arrayType);
            for (int i = 0; i < array.length; i++) {
                Object element = generator.generate(randomizer, parameter);
                array[i] = (T) element;
            }
        }

        return array;
    }

    private T[] allocateArray(RandomParameter parameter) {
        Object[] array = (parameter.length() <= 0)
                ? EMPTY_ARRAY
                : new Object[parameter.length()];
        return (T[]) array;
    }
}
