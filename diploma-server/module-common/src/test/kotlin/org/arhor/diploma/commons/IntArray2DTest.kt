package org.arhor.diploma.commons

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.random.Random
import kotlin.streams.asStream

internal class IntArray2DTest {

    @ParameterizedTest(name = "should create 2D array of size {0}x{1} filled with {2}")
    @MethodSource("provide rows, cols and initial value")
    fun `IntArray2D#create`(rows: Int, cols: Int, initialValue: Int) {
        // when
        val array = IntArray2D.create(rows, cols, initialValue)

        // then
        assertThat(array).isNotNull
        assertThat(array.rows).isEqualTo(rows)
        assertThat(array.cols).isEqualTo(cols)

        array.forEach { value -> assertThat(value).isEqualTo(initialValue) }
    }

    @ParameterizedTest(name = "should modify only value with [{0}, {1}] index")
    @MethodSource("provide array indices to test")
    fun `IntArray2D#set`(testRow: Int, testCol: Int) {
        // given
        val array = IntArray2D.create(TEST_ROWS, TEST_COLS)

        // when
        array[testRow, testCol] = 1

        // then
        array.forEach { value, row, col ->
            if ((row == testRow) && (col == testCol)) {
                assertThat(value).isOne
            } else {
                assertThat(value).isZero
            }
        }
    }

    @Suppress("UNUSED")
    companion object {
        private const val TEST_ROWS = 5
        private const val TEST_COLS = 5

        @JvmStatic
        fun `provide array indices to test`() = sequence {
            for (testRow in 0 until TEST_ROWS) {
                for (testCol in 0 until TEST_COLS) {
                    yield(Arguments.of(testRow, testCol))
                }
            }
        }.asStream()

        @JvmStatic
        fun `provide rows, cols and initial value`() = sequence {
            for (rows in 1..TEST_ROWS) {
                for (cols in 1..TEST_COLS) {
                    yield(Arguments.of(rows, cols, Random.nextInt(0, 100)))
                }
            }
        }.asStream()
    }
}