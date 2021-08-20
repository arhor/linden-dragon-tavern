package org.arhor.diploma.web.api

import org.arhor.diploma.service.NotificationService
import org.arhor.diploma.service.dto.NotificationDTO
import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.http.codec.ServerSentEvent
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/notifications")
class NotificationController(private val service: NotificationService) {

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = ["/stream"], produces = [TEXT_EVENT_STREAM_VALUE])
    suspend fun subscribe(auth: Authentication): Flux<ServerSentEvent<NotificationDTO>> {
        return service.subscribeForNotifications(auth.name)
    }

    @PostMapping
    suspend fun sendNotification(@RequestParam accountId: Long, @RequestBody notification: NotificationDTO) {
        service.persistNotification(accountId, notification)
    }
}

