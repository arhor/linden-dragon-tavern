package com.github.arhor.linden.dragon.tavern.service

import com.github.arhor.linden.dragon.tavern.service.dto.NotificationDTO
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface NotificationService {

    fun subscribeForNotifications(subscriberUsername: String): SseEmitter

    fun persistNotification(accountId: Long, notification: NotificationDTO)
}
