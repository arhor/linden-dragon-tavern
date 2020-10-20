package org.arhor.diploma.commons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class BitSet32Test {

    @Test
    void shouldHaveAllValuesFalse() {
        // given
        var bitSet = new BitSet32();

        for (int i = 0; i < bitSet.size(); i++) {
            // when
            boolean value = bitSet.get(i);

            // then
            assertThat(value).isFalse();
        }
    }

    @Test
    void shouldHaveAllValuesTrue() {
        // given
        var bitSet = new BitSet32();

        // when
        for (int i = 0; i < bitSet.size(); i++) {
            bitSet.set(i, true);
        }

        // then
        for (int i = 0; i < bitSet.size(); i++) {
            assertThat(bitSet.get(i)).isTrue();
        }
    }

    @ParameterizedTest
    @MethodSource("provideIndexesToTest")
    void shouldHaveOnlyOneValuesSet(int indexToTest) {
        // given
        var bitSet = new BitSet32();

        // when
        bitSet.set(indexToTest, true);

        // then
        for (int i = 0; i < bitSet.size(); i++) {
            if (i == indexToTest) {
                assertThat(bitSet.get(i)).isTrue();
            } else {
                assertThat(bitSet.get(i)).isFalse();
            }
        }
    }

    @Test
    void shouldHaveSeparateIterators() {
        // given
        var bitSet = new BitSet32();

        // when
        var iterator1 = bitSet.iterator();
        var iterator2 = bitSet.iterator();

        // then
        assertThat(iterator1).isNotSameAs(iterator2);
    }

    @ParameterizedTest
    @MethodSource("provideIndexesToTest")
    void shouldCorrectlyCreateBitSetFromNumber(int indexToTest) {
        // given
        var bitSet1 = new BitSet32();

        // when
        bitSet1.set(indexToTest, true);
        var bitSet2 = BitSet32.fromNumber(bitSet1.getValuesHolder());

        // then
        assertThat(bitSet2.getValuesHolder()).isEqualTo(bitSet1.getValuesHolder());

        for (int i = 0; i < BitSet32.CAPACITY; i++) {
            boolean value1 = bitSet1.get(i);
            boolean value2 = bitSet2.get(i);
            assertThat(value1).isEqualTo(value2);
        }
    }

    private static IntStream provideIndexesToTest() {
        return IntStream.range(0, BitSet32.CAPACITY);
    }
}
