package com.github.arhor.linden.dragon.tavern.service.event

import mu.KLogging
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class UserLoadedEventListener {

    @Async
    @EventListener(UserLoadedEvent::class)
    fun handleUserLoadedEvent(event: UserLoadedEvent) {
        logger.debug("OAuth2User user loaded: {}", event.principal.name)
    }

    companion object : KLogging()
}
