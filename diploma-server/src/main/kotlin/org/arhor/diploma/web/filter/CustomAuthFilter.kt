package org.arhor.diploma.web.filter

import org.arhor.diploma.service.AccountService
import org.arhor.diploma.util.createLogger
import org.arhor.diploma.web.security.TokenProvider
import org.slf4j.Logger
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthFilter(
    private val tokenProvider: TokenProvider<*>,
    private val accountService: AccountService
) : OncePerRequestFilter() {

  companion object {
    @JvmStatic
    private val log: Logger = createLogger<CustomAuthFilter>()
  }

  override fun doFilterInternal(
      req: HttpServletRequest,
      res: HttpServletResponse,
      next: FilterChain
  ) {
    try {
      req.getHeader(tokenProvider.authHeaderName())
          ?.let(tokenProvider::parse)
          ?.takeIf(tokenProvider::validate)
          ?.let(tokenProvider::parseUsername)
          ?.let(accountService::loadUserByUsername)
          ?.let { user ->
            val auth =
                UsernamePasswordAuthenticationToken(
                    user,
                    null, // TODO: what should be placed here? token, password or null?
                    user.authorities)
            auth.details = WebAuthenticationDetailsSource().buildDetails(req)
            SecurityContextHolder.getContext().authentication = auth
          }
    } catch (e: Exception) {
      log.error("Can NOT set user authentication -> Message: {}", e.message)
    }
    next.doFilter(req, res)
  }
}
