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

    @PostMapping("/sign-up")
    fun register(@RequestBody signUp: SignUpRequest): AuthResponse {
        TODO("IMPLEMENT ME!!!")
    }

    @PostMapping("/sign-in")
    fun authenticate(@RequestBody signIn: SignInRequest): AuthResponse {
        log.debug("authentication started: [${signIn}]")

        val auth = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                signIn.username,
                signIn.password
            )
        )

        return auth.toAuthResponse { log.debug("token granted: [{}]", it.accessToken) }
    }

    @GetMapping("/refresh")
    @PreAuthorize("isAuthenticated()")
    fun refresh(auth: Authentication): AuthResponse {
        log.debug("refreshing token: [${auth}]")
        return auth.toAuthResponse { log.debug("token refreshed: [{}]", it.accessToken) }
    }

    private inline fun Authentication.toAuthResponse(additionalAction: (AuthResponse) -> Unit = {}): AuthResponse {
        return AuthResponse(
            tokenProvider.generate(principal as UserDetails),
            tokenProvider.authTokenType
        ).also(additionalAction)
    }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}
