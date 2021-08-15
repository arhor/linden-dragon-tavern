package org.arhor.diploma.web.api

import mu.KLogging
import org.arhor.diploma.commons.time.DAY
import org.arhor.diploma.commons.time.SECOND
import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.http.codec.ServerSentEvent
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.EmitResult.*
import java.time.LocalTime
import java.util.*

@RestController
@RequestMapping("/api/notifications/stream")
class NotificationController {

    private val subscribers = HashMap<UUID, Sinks.Many<ServerSentEvent<String>>>()

    // TODO: try implement an ability to connect/re-connect for the events consumer
    @GetMapping(produces = [TEXT_EVENT_STREAM_VALUE])
    fun notificationsStream(): Flux<ServerSentEvent<String>> {
        val sessionId = UUID.randomUUID()
        val session = Sinks.many().replay().latest<ServerSentEvent<String>>()
        subscribers[sessionId] = session
        return session.asFlux()
    }

    @Scheduled(fixedDelay = 5 * SECOND, initialDelay = SECOND)
    fun sendNotification() {
        for ((id, session) in subscribers) {
            logger.debug { "sending notification to the consumer with session-id: $id" }
            session.tryEmitNext(
                ServerSentEvent.builder<String>()
                    .id(UUID.randomUUID().toString())
                    .event("notification-event")
                    .data("SSE - ${LocalTime.now()}")
                    .build()
            ).also(::handleEmittingResult)
        }
    }

    @Scheduled(fixedDelay = DAY, initialDelay = DAY)
    fun resetAllSessions() {
        for ((id, session) in subscribers) {
            logger.debug { "closing session with id: $id" }
            session.tryEmitComplete().also(::handleEmittingResult)
        }
    }

    private fun handleEmittingResult(result: Sinks.EmitResult) {
        when (result) {
            OK -> logger.debug { result }
            FAIL_NON_SERIALIZED -> logger.debug { result }
            FAIL_TERMINATED -> logger.debug { result }
            FAIL_OVERFLOW -> logger.debug { result }
            FAIL_CANCELLED -> logger.debug { result }
            FAIL_ZERO_SUBSCRIBER -> logger.debug { result }
        }
    }

    companion object : KLogging()
}