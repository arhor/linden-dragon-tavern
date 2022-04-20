package com.github.arhor.linden.dragon.tavern.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StringSanitizerImplTest {

    private StringSanitizerImpl sanitizer;

    @BeforeEach
    void setup() {
        sanitizer = new StringSanitizerImpl();
    }

    @Test
    @DisplayName("sanitize should return safe string for an input containing script injection")
    void sanitize_stringScriptInjection_safeString() {
        // given
        var initialUserInput = "<script>alert('Hacked!');</script>";

        // when
        var sanitizedUserInput = sanitizer.sanitize(initialUserInput);

        // then
        assertThat(sanitizedUserInput)
            .isNotNull()
            .isNotBlank()
            .isNotEqualTo(initialUserInput)
            .doesNotContain("<", ">");
    }

    @Test
    @DisplayName("sanitize should return the same string for an input without unsafe elements")
    void sanitize_safeString_stringEqualsInput() {
        // given
        var initialUserInput = "Just simple text without unsafe elements";

        // when
        var sanitizedUserInput = sanitizer.sanitize(initialUserInput);

        // then
        assertThat(sanitizedUserInput)
            .isNotNull()
            .isNotBlank()
            .isEqualTo(initialUserInput);
    }
}
