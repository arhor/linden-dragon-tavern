package com.github.arhor.linden.dragon.tavern.web.error

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.github.arhor.linden.dragon.tavern.web.error.ErrorCode.Type.DAT
import com.github.arhor.linden.dragon.tavern.web.error.ErrorCode.Type.GEN
import com.github.arhor.linden.dragon.tavern.web.error.ErrorCode.Type.SEC
import com.github.arhor.linden.dragon.tavern.web.error.ErrorCode.Type.VAL

@JsonSerialize(using = ErrorCodeSerializer::class)
enum class ErrorCode(val type: Type, val code: Int) {
    // @formatter:off

    UNCATEGORIZED      (type = GEN, code = 0),
    FILE_NOT_FOUND     (type = GEN, code = 1),

    VALIDATION_FAILED  (type = VAL, code = 0),

    SECURITY_VIOLATION (type = SEC, code = 0),

    DATA_ACCESS_ERROR  (type = DAT, code = 0),
    NOT_FOUND          (type = DAT, code = 1),

    // @formatter:on
    ;

    enum class Type(val description: String) {
        GEN("GENERAL"),
        SEC("SECURITY"),
        VAL("VALIDATION"),
        DAT("DATA")
        ;
    }
}
