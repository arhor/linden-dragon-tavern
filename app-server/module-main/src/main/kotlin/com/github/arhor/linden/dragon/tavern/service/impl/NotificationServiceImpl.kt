package com.github.arhor.linden.dragon.tavern.service.impl

import com.github.arhor.linden.dragon.tavern.common.time.SECOND
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.Notification
import com.github.arhor.linden.dragon.tavern.data.persistence.repository.AccountRepository
import com.github.arhor.linden.dragon.tavern.data.persistence.repository.NotificationRepository
import com.github.arhor.linden.dragon.tavern.exception.EntityNotFoundException
import com.github.arhor.linden.dragon.tavern.service.NotificationService
import com.github.arhor.linden.dragon.tavern.service.dto.NotificationDTO
import com.github.arhor.linden.dragon.tavern.service.mapping.NotificationConverter
import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.UUID

@Service
class NotificationServiceImpl(
    private val accountRepository: AccountRepository,
    private val notificationRepository: NotificationRepository,
    private val notificationConverter: NotificationConverter,
) : NotificationService {

    private val subscribers = HashMap<Long, SseEmitter>()

    override fun subscribeForNotifications(subscriberUsername: String): SseEmitter {
        val (accountId) = accountRepository.findByUsername(subscriberUsername)
            ?: throw EntityNotFoundException("Account", "username = $subscriberUsername")

        return subscribers.computeIfAbsent(accountId!!) { SseEmitter() }
    }

    override fun persistNotification(accountId: Long, notification: NotificationDTO) {
        val notificationEntity = notificationConverter.mapDtoToEntity(notification)
        notificationEntity.accountId = accountId
        notificationRepository.save(notificationEntity)
    }

    @Scheduled(fixedDelay = 30 * SECOND, initialDelay = 10 * SECOND)
    fun findAndSendNotificationsToSubscribers() {
        subscribers.keys.takeIf { it.isNotEmpty() }?.let { accountIds ->
            for (notification in notificationRepository.findAllByAccountIdsWithLock(accountIds)) {
                val emitter = subscribers[notification.accountId]
                if (emitter != null) {
                    emitter.send(notificationEvent(notification))
                    notificationRepository.delete(notification)
                }
            }
        }
    }

    private fun notificationEvent(notification: Notification): SseEmitter.SseEventBuilder {
        val eventId = UUID.randomUUID().toString()
        val notificationDTO = notificationConverter.mapEntityToDto(notification)

        return SseEmitter.event()
            .id(eventId)
            .name(NOTIFICATION_EVENT)
            .data(notificationDTO)
    }

    companion object : KLogging() {
        const val NOTIFICATION_EVENT = "notification-event"
    }
}
