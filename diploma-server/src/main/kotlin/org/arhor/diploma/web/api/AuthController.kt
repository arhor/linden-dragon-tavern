package org.arhor.diploma.web.api

import org.arhor.diploma.web.model.JwtResponse
import org.arhor.diploma.web.model.SignInRequest
import org.arhor.diploma.web.security.JwtProvider
import org.arhor.diploma.web.security.TokenProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    path = ["/auth"],
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController(
    private val authManager: AuthenticationManager,
    private val tokenProvider: TokenProvider<Authentication>
) {

  companion object {
    @JvmStatic
    private val log: Logger = LoggerFactory.getLogger(AuthController::class.java)
  }

  @PostMapping("/token")
  fun authenticate(@RequestBody signIn: SignInRequest): JwtResponse {
    val auth = authManager.authenticate(
        UsernamePasswordAuthenticationToken(
            signIn.username,
            signIn.password
        )
    )
    SecurityContextHolder.getContext().authentication = auth
    return JwtResponse(
        tokenProvider.generate(auth),
        JwtProvider.TOKEN_TYPE
    )
  }

  @GetMapping("/refresh")
  @PreAuthorize("isAuthenticated()")
  fun refresh(auth: Authentication): JwtResponse {
    return JwtResponse(
        tokenProvider.generate(auth),
        JwtProvider.TOKEN_TYPE
    )
  }
}
