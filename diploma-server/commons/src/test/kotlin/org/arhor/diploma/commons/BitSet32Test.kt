package org.arhor.diploma.commons

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.IntStream

class BitSet32Test {

    @Test
    fun shouldHaveAllValuesFalse() {
        // given
        val bitSet = BitSet32()

        for (i in 0 until bitSet.size()) {
            // when
            val value = bitSet[i]

            // then
            assertThat(value).isFalse
        }
    }

    @Test
    fun shouldHaveAllValuesTrue() {
        // given
        val bitSet = BitSet32()

        // when
        for (i in 0 until bitSet.size()) {
            bitSet[i] = true
        }

        // then
        for (i in 0 until bitSet.size()) {
            assertThat(bitSet[i]).isTrue
        }
    }

    @ParameterizedTest
    @MethodSource("provideIndexesToTest")
    fun shouldHaveOnlyOneValuesSet(indexToTest: Int) {
        // given
        val bitSet = BitSet32()

        // when
        bitSet[indexToTest] = true

        // then
        for (i in 0 until bitSet.size()) {
            if (i == indexToTest) {
                assertThat(bitSet[i]).isTrue
            } else {
                assertThat(bitSet[i]).isFalse
            }
        }
    }

    @Test
    fun shouldHaveSeparateIterators() {
        // given
        val bitSet = BitSet32()

        // when
        val iterator1 = bitSet.iterator()
        val iterator2 = bitSet.iterator()

        // then
        assertThat(iterator1).isNotSameAs(iterator2)
    }

    @ParameterizedTest
    @MethodSource("provideIndexesToTest")
    fun shouldCorrectlyCreateBitSetFromNumber(indexToTest: Int) {
        // given
        val bitSet1 = BitSet32()

        // when
        bitSet1[indexToTest] = true
        val bitSet2 = BitSet32.fromNumber(bitSet1.valuesHolder)

        // then
        assertThat(bitSet2.valuesHolder).isEqualTo(bitSet1.valuesHolder)

        for (i in  0 until BitSet32.CAPACITY) {
            val value1 = bitSet1[i]
            val value2 = bitSet2[i]

            assertThat(value1).isEqualTo(value2)
        }
    }

    companion object {
        @JvmStatic
        private fun provideIndexesToTest(): IntStream {
            return IntStream.range(0, BitSet32.CAPACITY)
        }
    }
}