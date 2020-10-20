package org.arhor.diploma.commons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Testable
//@ExtendWith(MockitoExtension.class)
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
    @MethodSource("provideArrayAndRandomCell")
    void shouldSetExactlyOneCell(int rows, int cols, int testRow, int testCol) {
        // when
        final var array = IntArray2D.create(rows, cols);
        array.set(testRow, testCol, 1);

        // then
        assertThat(array)
                .isNotNull()
                .hasFieldOrPropertyWithValue("rows", rows)
                .hasFieldOrPropertyWithValue("cols", cols);

        for (int row = 0; row < array.rows; row++) {
            for (int col = 0; col < array.cols; col++) {
                if ((row == testRow) && (col == testCol)) {
                    assertThat(array.get(row, col)).isOne();
                } else {
                    assertThat(array.get(row, col)).isZero();
                }
            }
        }
    }

    private static Stream<Arguments> provideArrayAndRandomCell() {
        return sequence().flatMap(cols -> {
            return sequence().map(rows -> {
                final int testCol = TestUtils.randomInt(cols);
                final int testRow = TestUtils.randomInt(rows);

                return Arguments.of(cols, rows, testCol, testRow);
            });
        });
    }

    private static Stream<Integer> sequence() {
        return IntStream.rangeClosed(1, 10).boxed();
    }
}