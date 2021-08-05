package org.arhor.diploma.commons

import org.arhor.diploma.commons.ActionResult.Companion.executeCatching
import org.arhor.diploma.commons.ActionResult.Companion.success
import org.arhor.diploma.commons.ActionResult.Failure
import org.arhor.diploma.commons.ActionResult.Success
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ActionResultTest {

    @Test
    fun `executeCatching should return Success instance`() {
        // given
        val message = "success message"
        val action = { message }

        // when
        val result = executeCatching(action)

        // then
        assertThat(result.isFailure).isFalse
        assertThat(result.isSuccess).isTrue
        assertThat(result).isInstanceOf(Success::class.java)
        assertThat((result as Success).value).isEqualTo(message)
    }

    @Test
    fun `executeCatching should return Failure instance`() {
        // given
        val error = Exception()
        val action = { throw error }

        // when
        val result = executeCatching(action)

        // then
        assertThat(result.isFailure).isTrue
        assertThat(result.isSuccess).isFalse
        assertThat(result).isInstanceOf(Failure::class.java)
        assertThat((result as Failure).error).isEqualTo(error)
    }

    @Test
    fun `map should return Success result on non-throwing action`() {
        // given
        val initial = 0
        val increment = { x: Int? -> (x ?: 0) + 1 }

        // when
        val result = success(initial)
            .map(increment)
            .map(increment)
            .map(increment)

        // then
        assertThat(result).isInstanceOf(Success::class.java)
        assertThat((result as Success).value).isEqualTo(3)
    }

    @Test
    fun `map should return Failure result on throwing action`() {
        // given
        val simpleAction: (Any?) -> Any? = { it }
        val throwingException: (Any?) -> Any? = { throw Exception() }

        // when
        val result = success(0)
            .map(simpleAction)
            .map(throwingException)

        // then
        assertThat(result).isInstanceOf(Failure::class.java)
    }
}
