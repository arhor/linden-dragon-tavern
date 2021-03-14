package org.arhor.diploma.web.api

import org.arhor.diploma.web.model.AuthResponse
import org.arhor.diploma.web.model.SignInRequest
import org.arhor.diploma.web.model.SignUpRequest
import org.arhor.diploma.web.security.TokenProvider
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.lang.invoke.MethodHandles

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
        log.debug("authentication started: [${signIn}]")

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

        log.debug("token granted: [{}]", authResponse.accessToken)

        return authResponse
    }

    @GetMapping("/refresh")
    @PreAuthorize("isAuthenticated()")
    fun refresh(auth: Authentication): AuthResponse {
        log.debug("refreshing token: [${auth}]")

        val authResponse = AuthResponse(
            tokenProvider.generate(auth.principal as UserDetails),
            tokenProvider.authTokenType
        )

        log.debug("token refreshed: [{}]", authResponse.accessToken)

        return authResponse
    }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}
