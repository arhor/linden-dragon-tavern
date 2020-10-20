package org.arhor.diploma.web.model

import com.fasterxml.jackson.annotation.JsonPropertyOrder

enum class Severity { INFO, WARN, ERROR }

@JsonPropertyOrder("severity", "code", "text", "details")
data class Message(
    val severity: Severity,
    val code: Int?,
    val text: String?,
    val details: List<Any>?
)

class MessageBuilder(
    val severity: Severity,
    var code: Int? = null,
    var text: String? = null,
    var details: List<Any>? = null
)

/**
 * Represents general message response from the server.
 *
 * JSON form:
 *
 * {
 *   messages: [
 *     {
 *       code: 400,
 *       severity: error,
 *       text: "Validation failed",
 *       details: [
 *         "field 'username' should not be blank",
 *         "field 'password' should not be null"
 *       ]
 *     }
 *   ]
 * }
 *
 * @author Maksim Buryshynets
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
