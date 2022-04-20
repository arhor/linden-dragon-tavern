package com.github.arhor.linden.dragon.tavern.web.error.temp;

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import mu.KLogging

class ErrorCodeSerializer : StdSerializer<ErrorCode>(ErrorCode::class.java) {

    override fun serialize(value: ErrorCode, generator: JsonGenerator, provider: SerializerProvider) {
        val type = value.type.name
        val code = convertCodeToPaddedString(value);
        generator.writeString("$type-$code");
    }

    private fun convertCodeToPaddedString(value: ErrorCode): String {
        val numberAsString = value.numericValue.toString()
        if (numberAsString.length > NUM_CODE_MAX_LENGTH) {
            logger.debug("ErrorCode_Deprecated {} numeric value is too large", value);
        }
        return numberAsString.padStart(length = NUM_CODE_MAX_LENGTH, padChar = NUM_CODE_PAD_SYMBOL)
    }

    companion object : KLogging() {
        private const val NUM_CODE_MAX_LENGTH = 5
        private const val NUM_CODE_PAD_SYMBOL = '0'
    }
}
