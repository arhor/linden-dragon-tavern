package org.arhor.diploma.web.security

import org.arhor.diploma.util.createLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        req: HttpServletRequest,
        res: HttpServletResponse,
        err: AuthenticationException
    ) {
        log.error("Unauthorized error. Message - {}", err.message)
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized")
    }

    companion object {
        @JvmStatic
        private val log: Logger = createLogger<JwtAuthEntryPoint>()
    }
}
