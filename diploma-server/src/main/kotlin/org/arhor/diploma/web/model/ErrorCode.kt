package org.arhor.diploma.web.model

import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(using = ErrorCodeSerializer::class)
enum class ErrorCode(val type: Type, val code: Int) {

    UNCATEGORIZED      (type = Type.GEN, code = 0),

    VALIDATION_FAILED  (type = Type.VAL, code = 0),

    SECURITY_VIOLATION (type = Type.SEC, code = 0),

    ;

    enum class Type(val description: String) {
        GEN("GENERAL"),
        SEC("SECURITY"),
        VAL("VALIDATION"),
        ;
    }
}
