package org.arhor.diploma.web.model

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime

@JsonPropertyOrder("severity", "timestamp", "code", "text", "details")
data class Message(
    val severity: Severity,
    val timestamp: LocalDateTime,
    val code: ErrorCode,
    val text: String? = null,
    val details: List<Any>?
) {

    enum class Severity { INFO, WARN, ERROR }
}

/**
 * Represents general message response from the server.
 *
 * JSON form:
 * ```
 * {
 *   "messages": [
 *     {
 *       "severity": "ERROR",
 *       "timestamp": "2021-02-24T13:03:24.7504",
 *       "code": "GEN-00000",
 *       "text": "Validation failed",
 *       "details": [
 *         "field 'username' should not be blank",
 *         "field 'password' should not be null"
 *       ]
 *     }
 *   ]
 * }
 * ```
 */
data class MessageResponse(val messages: List<Message>)

class MessageBuilder(
    val severity: Message.Severity,
    var timestamp: LocalDateTime = LocalDateTime.now(),
    var code: ErrorCode = ErrorCode.UNCATEGORIZED,
    var text: String? = null,
    var details: List<Any?> = emptyList()
)

@DslMarker
annotation class MessageResponseMaker

@MessageResponseMaker
class MessageResponseBuilder {

    private val messages = mutableListOf<Message>()

    fun info(init: MessageBuilder.() -> Unit) = initMessage(Message.Severity.INFO, init)

    fun warn(init: MessageBuilder.() -> Unit) = initMessage(Message.Severity.WARN, init)

    fun error(init: MessageBuilder.() -> Unit) = initMessage(Message.Severity.ERROR, init)

    fun build(): MessageResponse {
        return MessageResponse(messages.toList())
    }

    private inline fun initMessage(severity: Message.Severity, init: MessageBuilder.() -> Unit) {
        val builder = MessageBuilder(severity)

        builder.init()

        messages.add(
            Message(
                severity = builder.severity,
                timestamp = builder.timestamp,
                code = builder.code,
                text = builder.text,
                details = builder.details.filterNotNull()
            )
        )
    }
}

inline fun messageResponse(init: MessageResponseBuilder.() -> Unit): MessageResponse {
    val message = MessageResponseBuilder()
    message.init()
    return message.build()
}
