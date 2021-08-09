package org.arhor.diploma.web.filter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class SpaWebFilterTest {

    private lateinit var filter: SpaWebFilter

    @BeforeEach
    fun setUp() {
        filter = SpaWebFilter()
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "/api/v1/accounts",
            "/api/v1/monsters",
            "/api/v1/spells",
            "/api/charsheets",
        ]
    )
    fun `should return false for API-calls`(path: String) {
        // when
        val result = filter.shouldBeForwardedToTheRootPath(path)

        // then
        assertThat(result)
            .isFalse
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "/css/app.41cbab89.css",
            "/css/chunk-6c1c7df0.457609c4.css",
            "/css/chunk-vendors.1efe0f61.css",
            "/js/chunk-2d0a465b.ddfdb17a.js",
            "/pics/maps/Barovia/baroviaregion.png",
            "/img/logo.3be58095.svg",
            "/fonts/materialdesignicons-webfont.7a44ea19.woff2",
        ]
    )
    fun `should return false for static-resource request`(path: String) {
        // when
        val result = filter.shouldBeForwardedToTheRootPath(path)

        // then
        assertThat(result)
            .isFalse
    }
}