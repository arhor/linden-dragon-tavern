package com.github.arhor.linden.dragon.tavern.service.event

import org.springframework.security.core.AuthenticatedPrincipal

data class UserLoadedEvent(val principal: AuthenticatedPrincipal)
