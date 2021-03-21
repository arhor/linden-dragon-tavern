package org.arhor.diploma.commons;

import org.arhor.diploma.commons.ActionResult.Success;
import org.arhor.diploma.commons.ActionResult.Failure;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActionResultTest {

    @Test
    void shouldSuccess() {
        final var message = "success message";

        final var result = new Success<>(message);

        assertThat(result.isFailure()).isFalse();
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(message);
    }

    @Test
    void shouldFailure() {
        final var error = new Exception();
        final var result = new Failure(error);

        assertThat(result.isFailure()).isTrue();
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getError()).isEqualTo(error);
    }
}
