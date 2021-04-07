package org.arhor.diploma.web.security

import mu.KotlinLogging
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val logger = KotlinLogging.logger {}

@Component
class ApplicationAuthEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        req: HttpServletRequest,
        res: HttpServletResponse,
        exc: AuthenticationException
    ) {
        logger.error(exc) { "Unauthorized error. Message - ${exc.message}" }
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized")
    }
}
