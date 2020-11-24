package org.arhor.diploma.testutils;

import org.assertj.core.internal.bytebuddy.utility.RandomString;

import java.util.Random;
import java.util.Set;

enum PrimitiveGenerator implements ParameterGenerator {

    BYTES(byte.class, Byte.class) {
        @Override
        public Byte generate(Random randomizer, RandomParameter parameter) {
            var byteHolder = new byte[1];
            randomizer.nextBytes(byteHolder);
            return byteHolder[0];
        }
    },

    SHORTS(short.class, Short.class) {
        @Override
        public Short generate(Random randomizer, RandomParameter parameter) {
            return (short) randomizer.nextInt();
        }
    },

    INTEGERS(int.class, Integer.class) {
        @Override
        public Integer generate(Random randomizer, RandomParameter parameter) {
            final long min = parameter.min();
            final long max = parameter.max();

            if ((min <= Integer.MIN_VALUE) && (max >= Integer.MAX_VALUE)) {
                return randomizer.nextInt();
            }

            return randomizer
                    .ints()
                    .filter(value -> (value >= min) && (value <= max))
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
        public Long generate(Random randomizer, RandomParameter parameter) {
            return randomizer.nextLong();
        }
    },

    FLOATS(float.class, Float.class) {
        @Override
        public Float generate(Random randomizer, RandomParameter parameter) {
            return randomizer.nextFloat();
        }
    },

    DOUBLES(double.class, Double.class) {
        @Override
        public Double generate(Random randomizer, RandomParameter parameter) {
            return randomizer.nextDouble();
        }
    },

    BOOLEANS(boolean.class, Boolean.class) {
        @Override
        public Boolean generate(Random randomizer, RandomParameter parameter) {
            return randomizer.nextBoolean();
        }
    },

    CHARACTERS(char.class, Character.class) {

        private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

        @Override
        public Character generate(Random randomizer, RandomParameter parameter) {
            final int charIndex = randomizer.nextInt(ALPHABET.length());
            final boolean isUpperCase = randomizer.nextBoolean();

            String character = ALPHABET.substring(charIndex, charIndex + 1);

            if (isUpperCase) {
                character = character.toUpperCase();
            }

            return character.charAt(0);
        }
    },

    CHARACTER_SEQUENCES(String.class) {
        @Override
        public String generate(Random randomizer, RandomParameter parameter) {
            return (parameter.length() > 0)
                    ? RandomString.make(parameter.length())
                    : RandomString.make();
        }
    },

    ;

    private final Set<Class<?>> supportedTypes;

    PrimitiveGenerator(Class<?>... types) {
        this.supportedTypes = Set.of(types);
    }

    public static boolean globallySupportsType(Class<?> type) {
        for (var generator : values()) {
            if (generator.supportsType(type)) {
                return true;
            }
        }
        return false;
    }

    public boolean supportsType(Class<?> type) {
        return supportedTypes.contains(type);
    }
}
