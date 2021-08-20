package org.arhor.diploma.web.api

import mu.KLogging
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/login")
    suspend fun login(auth: Authentication): Collection<GrantedAuthority> {
        logger.debug { "User [${auth.name}] logged in" }
        return auth.authorities
    }

    companion object : KLogging()
}
