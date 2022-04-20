package com.github.arhor.linden.dragon.tavern.testutils;

import java.lang.reflect.Array;
import java.util.Random;

@SuppressWarnings("unchecked")
final class ArrayGenerator<T> implements ParameterGenerator {

    private final Object[] emptyArray;
    private final Class<T> arrayType;

    ArrayGenerator(Class<T> arrayType) {
        this.arrayType = arrayType;
        this.emptyArray = (T[]) Array.newInstance(arrayType, 0);
    }

    @Override
    public T[] generate(Random randomizer, RandomParameter parameter) {
        final T[] array = allocateArray(parameter);

        if (array != emptyArray) {
            var generator = ParameterGeneratorFactory.getGeneratorForType(arrayType);
            for (int i = 0; i < array.length; i++) {
                Object element = generator.generate(randomizer, parameter);
                array[i] = (T) element;
            }
        }

        return array;
    }

    private T[] allocateArray(RandomParameter parameter) {
        return (T[]) ((parameter.length() <= 0)
                ? emptyArray
                : Array.newInstance(arrayType, parameter.length()));
    }
}
