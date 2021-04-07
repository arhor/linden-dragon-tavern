package org.arhor.diploma.web.api

import mu.KotlinLogging
import org.arhor.diploma.web.model.AuthResponse
import org.arhor.diploma.web.model.SignInRequest
import org.arhor.diploma.web.security.TokenProvider
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping(
    path = ["/auth"],
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class AuthController(
    private val authManager: AuthenticationManager,
    private val tokenProvider: TokenProvider
) {

    @PostMapping("/sign-in")
    fun authenticate(@RequestBody signIn: SignInRequest): AuthResponse {
        logger.debug { "authentication started: [${signIn}]" }

        val auth = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                signIn.username,
                signIn.password
            )
        )

        val authResponse = AuthResponse(
            tokenProvider.generate(auth.principal as UserDetails),
            tokenProvider.authTokenType
        )

        logger.debug { "token granted: [${authResponse.accessToken}]"  }

        return authResponse
    }

    @GetMapping("/refresh")
    @PreAuthorize("isAuthenticated()")
    fun refresh(auth: Authentication): AuthResponse {
        logger.debug { "refreshing token: [${auth}]" }

        val authResponse = AuthResponse(
            tokenProvider.generate(auth.principal as UserDetails),
            tokenProvider.authTokenType
        )

        logger.debug { "token refreshed: [${authResponse.accessToken}]" }

        return authResponse
    }
}
