package com.github.arhor.linden.dragon.tavern.web.api

import com.github.arhor.linden.dragon.tavern.service.NotificationService
import com.github.arhor.linden.dragon.tavern.service.dto.NotificationDTO
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping("/api/notifications")
class NotificationController(private val service: NotificationService) {

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = ["/stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribe(auth: Authentication): SseEmitter {
        return service.subscribeForNotifications(auth.name)
    }

    @PostMapping
    fun sendNotification(@RequestParam accountId: Long, @RequestBody notification: NotificationDTO) {
        service.persistNotification(accountId, notification)
    }
}

