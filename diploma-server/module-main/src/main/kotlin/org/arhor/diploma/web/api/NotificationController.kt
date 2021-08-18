package org.arhor.diploma.web.api

import org.arhor.diploma.data.persistence.domain.Notification
import org.arhor.diploma.service.NotificationService
import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/notifications")
class NotificationController(private val service: NotificationService) {

    @GetMapping(path = ["/stream"], produces = [TEXT_EVENT_STREAM_VALUE])
    fun getNotificationsStream(@RequestParam accountId: Long): Flux<ServerSentEvent<Notification>> {
        return service.subscribeForNotifications(accountId)
    }

    @PostMapping
    suspend fun sendNotification(@RequestParam accountId: Long, @RequestBody notification: Notification) {

    }
}

