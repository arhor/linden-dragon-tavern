package org.arhor.diploma.commons;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.arhor.diploma.commons.CoreFunctions.coalesce;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CoreFunctionsTest {

    @Test
    void shouldReturnNullWhenArgsNotProvided() {
        // when
        Integer result = coalesce();

        assertThat(result)
                .isNull();
    }

    /* Anonymous is used instead of lambda since lambda cannot be wrapped by `spy` function */
    @Test
    void shouldReturnValueFromSecondSupplier() {
        // given
        final Supplier<Integer> first = spy(new Supplier<Integer>() {
            @Override
            public Integer get() { return null; }
        });
        final Supplier<Integer> second = spy(new Supplier<Integer>() {
            @Override
            public Integer get() { return 2; }
        });
        final Supplier<Integer> third = spy(new Supplier<Integer>() {
            @Override
            public Integer get() { return 3; }
        });

        // when
        Integer result = coalesce(first, second, third);

        assertThat(result)
                .isNotNull()
                .isEqualTo(2);

        verify(first).get();
        verify(second).get();
        verifyNoInteractions(third);
    }
}
