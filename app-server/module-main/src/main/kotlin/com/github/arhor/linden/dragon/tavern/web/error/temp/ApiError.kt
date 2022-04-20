package com.github.arhor.linden.dragon.tavern.web.error.temp;

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.io.Serializable
import java.time.Instant
import java.util.UUID

@JsonPropertyOrder(
    "code",
    "message",
    "details",
    "timestamp",
    "requestId",
)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiError(
    val requestId: UUID,
    val message: String,
    val code: ErrorCode = ErrorCode.UNCATEGORIZED,
    val details: List<String> = emptyList(),
    val timestamp: Instant,
) : Serializable
