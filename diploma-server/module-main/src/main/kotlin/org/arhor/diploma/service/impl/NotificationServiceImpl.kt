package org.arhor.diploma.service.impl

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.arhor.diploma.commons.time.MINUTE
import org.arhor.diploma.data.persistence.domain.Notification
import org.arhor.diploma.data.persistence.repository.NotificationRepository
import org.arhor.diploma.service.NotificationService
import org.springframework.http.codec.ServerSentEvent
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.EmitResult.*
import java.util.*
import kotlin.collections.ArrayList

@Service
class NotificationServiceImpl(private val repository: NotificationRepository) : NotificationService {

    private val subscribers = HashMap<Long, Sinks.Many<ServerSentEvent<Notification>>>()

    override fun subscribeForNotifications(accountId: Long): Flux<ServerSentEvent<Notification>> {
        return subscribers.computeIfAbsent(accountId) {
            Sinks.many().replay().latest()
        }.asFlux()
    }

    override suspend fun sendNotification(accountId: Long, notification: Notification) {
        sendNotificationInternal(accountId, notification) ?: run {
            repository.save(notification)
        }
    }

    @Transactional
    @Scheduled(fixedDelay = MINUTE, initialDelay = MINUTE)
    fun resetAllSessions() = runBlocking {
        val accountIds = subscribers.keys

        if (accountIds.isNotEmpty()) {
            val sentNotifications = ArrayList<Notification>()

            repository.findAllByAccountIds(accountIds).collect {
                val result = sendNotificationInternal(it.accountId, it)

                if (result == OK) {
                    sentNotifications.add(it)
                }
            }

            if (sentNotifications.isNotEmpty()) {
                repository.deleteAll(sentNotifications)
            }
        }
    }

    private fun sendNotificationInternal(accountId: Long, notification: Notification) =
        subscribers[accountId]?.tryEmitNext(
            ServerSentEvent.builder<Notification>()
                .id(UUID.randomUUID().toString())
                .event("notification-event")
                .data(notification)
                .build()
        )?.also(::handleEmittingResult)

    private fun handleEmittingResult(result: Sinks.EmitResult) {
        when (result) {
            OK,
            FAIL_NON_SERIALIZED,
            FAIL_TERMINATED,
            FAIL_OVERFLOW,
            FAIL_CANCELLED,
            FAIL_ZERO_SUBSCRIBER,
            -> logger.debug { result }
        }
    }

    companion object : KLogging()
}