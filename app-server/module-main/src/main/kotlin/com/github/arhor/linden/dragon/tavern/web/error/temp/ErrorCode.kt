package com.github.arhor.linden.dragon.tavern.web.error.temp

import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(using = ErrorCodeSerializer::class)
enum class ErrorCode(
    val type: Type,
    val numericValue: Int,
    val label: String,
) {
    UNCATEGORIZED             (Type.GEN, 0, "error.server.internal"),

    VALIDATION_FAILED         (Type.VAL, 0, "error.entity.validation.failed"),

    UNAUTHORIZED              (Type.SEC, 0, "error.server.unauthorized"),

    NOT_FOUND                 (Type.DAT, 0, "error.entity.not.found"),
    DUPLICATE                 (Type.DAT, 1, "error.entity.duplicate"),

    HANDLER_NOT_FOUND_DEFAULT (Type.SRV, 0, "error.server.handler.not.found.default"),
    HANDLER_NOT_FOUND         (Type.SRV, 1, "error.server.handler.not.found"),
    METHOD_ARG_TYPE_MISMATCH  (Type.SRV, 2, "error.value.type.mismatch"),
    ;
    enum class Type(val description: String) {
        GEN("GENERAL"),
        SEC("SECURITY"),
        VAL("VALIDATION"),
        DAT("DATA"),
        SRV("SERVER"),
        ;
    }
}
