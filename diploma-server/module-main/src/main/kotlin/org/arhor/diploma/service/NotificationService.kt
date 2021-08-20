package org.arhor.diploma.service

import org.arhor.diploma.service.dto.NotificationDTO
import org.springframework.http.codec.ServerSentEvent
import reactor.core.publisher.Flux

interface NotificationService {

    suspend fun subscribeForNotifications(subscriberUsername: String): Flux<ServerSentEvent<NotificationDTO>>

    suspend fun persistNotification(accountId: Long, notification: NotificationDTO)
}