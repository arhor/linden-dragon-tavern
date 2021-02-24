package org.arhor.diploma.web.model

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime

enum class Severity { INFO, WARN, ERROR }

@JsonPropertyOrder("severity", "timestamp", "code", "text", "details")
data class Message(
    val severity: Severity,
    val timestamp: LocalDateTime,
    val code: Int?,
    val text: String?,
    val details: List<Any>?
)

class MessageBuilder(
    val severity: Severity,
    var timestamp: LocalDateTime = LocalDateTime.now(),
    var code: Int? = null,
    var text: String? = null,
    var details: List<Any> = emptyList()
)

/**
 * Represents general message response from the server.
 *
 * JSON form:
 *
 * {
 *   "messages": [
 *     {
 *       "severity": "ERROR",
 *       "timestamp": "2021-02-24T13:03:24.7504",
 *       "code": 400,
 *       "text": "Validation failed",
 *       "details": [
 *         "field 'username' should not be blank",
 *         "field 'password' should not be null"
 *       ]
 *     }
 *   ]
 * }
 */
data class MessageResponse(val messages: List<Message>)

@DslMarker
annotation class MessageResponseMaker

@MessageResponseMaker
class MessageResponseBuilder {

    private val messages = mutableListOf<Message>()

    fun info(init: MessageBuilder.() -> Unit) = initMessage(Severity.INFO, init)

    fun warn(init: MessageBuilder.() -> Unit) = initMessage(Severity.WARN, init)

    fun error(init: MessageBuilder.() -> Unit) = initMessage(Severity.ERROR, init)

    fun build(): MessageResponse {
        return MessageResponse(messages.toList())
    }

    private inline fun initMessage(severity: Severity, init: MessageBuilder.() -> Unit) {
        val builder = MessageBuilder(severity)
        builder.init()

        messages.add(
            Message(
                severity = builder.severity,
                timestamp = builder.timestamp,
                code = builder.code,
                text = builder.text,
                details = builder.details
            )
        )
    }
}

inline fun messageResponse(init: MessageResponseBuilder.() -> Unit): MessageResponse {
    val message = MessageResponseBuilder()
    message.init()
    return message.build()
}
