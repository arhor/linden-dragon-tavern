package org.arhor.diploma.web.model

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

class ErrorCodeSerializer : StdSerializer<ErrorCode>(ErrorCode::class.java) {

    override fun serialize(value: ErrorCode, gen: JsonGenerator, provider: SerializerProvider) {
        val type = value.type.name
        val code = value.code.toString().padStart(NUM_CODE_MAX_LENGTH, NUM_CODE_PAD_SYMBOL)

        gen.writeString("$type-$code")
    }

    companion object {
        const val NUM_CODE_MAX_LENGTH = 5
        const val NUM_CODE_PAD_SYMBOL = '0'
    }
}