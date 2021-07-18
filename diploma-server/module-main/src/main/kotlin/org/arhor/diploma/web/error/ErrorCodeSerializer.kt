package org.arhor.diploma.web.error

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import mu.KotlinLogging

private const val NUM_CODE_MAX_LENGTH = 5
private const val NUM_CODE_PAD_SYMBOL = '0'

private val logger = KotlinLogging.logger {}

class ErrorCodeSerializer : StdSerializer<ErrorCode>(ErrorCode::class.java) {

    override fun serialize(value: ErrorCode, generator: JsonGenerator, provider: SerializerProvider) {
        val type = value.type.name
        val code = value.code.toString().let {
            if (it.length > NUM_CODE_MAX_LENGTH) {
                logger.debug { "ErrorCode $value numeric value is too large" }
            }
            it.padStart(NUM_CODE_MAX_LENGTH, NUM_CODE_PAD_SYMBOL)
        }

        generator.writeString("$type-${code}")
    }
}