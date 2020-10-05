package org.arhor.diploma.core;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.arhor.diploma.TestUtils.randomInt;
import static org.assertj.core.api.Assertions.assertThat;

@Testable
class IntArray2DTest {

    private static final Logger log = LoggerFactory.getLogger(IntArray2DTest.class);

    @Test
    void shouldCreateEmptyArrayWithDefinedSize() {
        // given
        final int rows = 3;
        final int cols = 3;

        // when
        final var array = IntArray2D.create(rows, cols);

        // then
        assertThat(array).isNotNull();
        assertThat(array.rows).isEqualTo(rows);
        assertThat(array.cols).isEqualTo(cols);

        for (int i = 0; i < array.rows; i++) {
            for (int j = 0; j < array.cols; j++) {
                assertThat(array.get(i, j)).isZero();
            }
        }
    }

    @Test
    void shouldCreateFilledArrayWithDefinedSize() {
        // given
        final int rows = 3;
        final int cols = 3;

        // when
        final var array = IntArray2D.create(rows, cols, (col, row) -> 1);

        // then
        assertThat(array).isNotNull();
        assertThat(array.rows).isEqualTo(rows);
        assertThat(array.cols).isEqualTo(cols);

        for (int i = 0; i < array.rows; i++) {
            for (int j = 0; j < array.cols; j++) {
                assertThat(array.get(i, j)).isOne();
            }
        }
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void shouldSetExactlyOneCell(int rows, int cols, int testRow, int testCol) {
        // when
        final var array = IntArray2D.create(rows, cols);
        array.set(testRow, testCol, 1);

        // then
        assertThat(array).isNotNull();
        assertThat(array.rows).isEqualTo(rows);
        assertThat(array.cols).isEqualTo(cols);

        for (int i = 0; i < array.rows; i++) {
            for (int j = 0; j < array.cols; j++) {
                if (i == testRow && j == testCol) {
                    assertThat(array.get(i, j)).isOne();
                } else {
                    assertThat(array.get(i, j)).isZero();
                }
            }
        }
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return sequence().flatMap(cols -> {
            return sequence().map(rows -> {
                final int testCol = randomInt(cols);
                final int testRow = randomInt(rows);

                return Arguments.of(cols, rows, testCol, testRow);
            });
        });
    }

    @NotNull
    private static Stream<Integer> sequence() {
        return IntStream.rangeClosed(1, 10).boxed();
    }
}