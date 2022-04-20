package com.github.arhor.linden.dragon.tavern.web.security

import org.springframework.security.core.Authentication

interface OwnerResolver {

    fun resolveOwnerId(authentication: Authentication): String
}
