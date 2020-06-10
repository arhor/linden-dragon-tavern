package org.arhor.diploma.web.security

import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@Component
class JwtAuthEntryPoint : AuthenticationEntryPoint {

  companion object {
    @JvmStatic
    private val log: Logger = LoggerFactory.getLogger(JwtAuthEntryPoint::class.java)
  }

  override fun commence(
      req: HttpServletRequest,
      res: HttpServletResponse,
      e: AuthenticationException
  ) {
    log.error("Unauthorized error. Message - {}", e.message)
    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized")
  }
}
