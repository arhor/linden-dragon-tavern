package org.arhor.diploma.web.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.lang.invoke.MethodHandles
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ApplicationAuthEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        req: HttpServletRequest,
        res: HttpServletResponse,
        err: AuthenticationException
    ) {
        log.error("Unauthorized error. Message - {}", err.message)
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized")
    }

    companion object {
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}
