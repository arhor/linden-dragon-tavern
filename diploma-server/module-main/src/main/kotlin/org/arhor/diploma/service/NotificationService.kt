package org.arhor.diploma.service

import org.arhor.diploma.data.persistence.domain.Notification
import org.springframework.http.codec.ServerSentEvent
import reactor.core.publisher.Flux

interface NotificationService {

    fun subscribeForNotifications(accountId: Long): Flux<ServerSentEvent<Notification>>

    suspend fun sendNotification(accountId: Long, notification: Notification)
}