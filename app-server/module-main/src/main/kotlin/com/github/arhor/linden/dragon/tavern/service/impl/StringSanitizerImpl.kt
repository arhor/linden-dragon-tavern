package com.github.arhor.linden.dragon.tavern.service.impl


import com.github.arhor.linden.dragon.tavern.service.StringSanitizer
import org.springframework.stereotype.Component


@Component
class StringSanitizerImpl : StringSanitizer {

    override fun sanitize(input: String?): String? {
        if (input != null) {
            var result: String = input
            for ((unsafeElement, replacement) in MAPPINGS.entries) {
                result = result.replace(unsafeElement, replacement)
            }
            return result
        }
        return null
    }

    companion object {
        private val MAPPINGS = mapOf(
            "<" to "&lt;",
            ">" to "&gt;",
            "&" to "&amp;",
            "\"" to "&quot;"
        )
    }
}
