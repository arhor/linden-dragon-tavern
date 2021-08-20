package org.arhor.diploma.service.impl

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.commons.time.SECOND
import org.arhor.diploma.data.persistence.domain.Notification
import org.arhor.diploma.data.persistence.repository.AccountRepository
import org.arhor.diploma.data.persistence.repository.NotificationRepository
import org.arhor.diploma.service.NotificationService
import org.arhor.diploma.service.dto.NotificationDTO
import org.arhor.diploma.service.mapping.NotificationConverter
import org.springframework.http.codec.ServerSentEvent
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.reactive.executeAndAwait
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.EmitResult.*
import java.util.*

@Service
class NotificationServiceImpl(
    private val transaction: TransactionalOperator,
    private val accountRepository: AccountRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationConverter: NotificationConverter,
) : NotificationService {

    private val subscribers = HashMap<Long, Sinks.Many<ServerSentEvent<NotificationDTO>>>()

    override suspend fun subscribeForNotifications(subscriberUsername: String): Flux<ServerSentEvent<NotificationDTO>> {
        val (accountId) = accountRepository.findByUsername(subscriberUsername)
            ?: throw EntityNotFoundException("Account", "username", subscriberUsername)

        return subscribers.computeIfAbsent(accountId!!) {
            Sinks.many().replay().latest()
        }.asFlux()
    }

    override suspend fun persistNotification(accountId: Long, notification: NotificationDTO) {
        val notificationEntity = notificationConverter.mapDtoToEntity(notification)
        notificationEntity.accountId = accountId
        notificationRepository.save(notificationEntity)
    }

    @Scheduled(fixedDelay = 30 * SECOND, initialDelay = 10 * SECOND)
    fun findAndSendNotificationsToSubscribers() = runBlocking {
        subscribers.keys.takeIf { it.isNotEmpty() }?.let { accountIds ->

            transaction.executeAndAwait {
                val sentNotifications = ArrayList<Notification>()

                notificationRepository.findAllByAccountIdsWithLock(accountIds).collect {
                    sendInternal(it, onSuccess = sentNotifications::add)
                }

                if (sentNotifications.isNotEmpty()) {
                    logger.debug { "${sentNotifications.size} notifications sent successfully, deleting them..." }
                    notificationRepository.deleteAll(sentNotifications)
                }
            }
        }
    }

    private inline fun sendInternal(
        notification: Notification,
        onSuccess: (Notification) -> Unit = {},
        onFailure: (Notification) -> Unit = {},
    ): Sinks.EmitResult? {

        return subscribers[notification.accountId]?.tryEmitNext(notificationEvent(notification))?.also { result ->

            logger.debug {
                "Notification id: ${notification.id}, account id: ${notification.accountId}, result: $result"
            }

            when (result) {
                OK -> {
                    onSuccess(notification)
                }
                FAIL_NON_SERIALIZED, FAIL_TERMINATED, FAIL_OVERFLOW, FAIL_CANCELLED, FAIL_ZERO_SUBSCRIBER -> {
                    onFailure(notification)
                }
            }
        }
    }

    private fun notificationEvent(notification: Notification): ServerSentEvent<NotificationDTO> {
        val eventId = UUID.randomUUID().toString()
        val notificationDTO = notificationConverter.mapEntityToDto(notification)

        return ServerSentEvent.builder<NotificationDTO>()
            .id(eventId)
            .event(NOTIFICATION_EVENT)
            .data(notificationDTO)
            .build()
    }

    companion object : KLogging() {
        const val NOTIFICATION_EVENT = "notification-event"
    }
}