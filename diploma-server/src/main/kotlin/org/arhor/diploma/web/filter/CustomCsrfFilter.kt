package org.arhor.diploma.web.filter

import org.arhor.diploma.CsrfUtils.CSRF_COOKIE_NAME
import org.arhor.diploma.CsrfUtils.CSRF_HEADER_NAME
import org.arhor.diploma.CsrfUtils.SAFE_METHODS
import org.springframework.security.web.access.AccessDeniedHandlerImpl
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException

/**
 * Class CustomCsrfFilter implements stateless CSRF protection. To successfully
 * pass a CSRF check, the request must contain the CSRF token generated on the
 * client side in the appropriate header and cookie, and the token in the header
 * must match the token in the cookie.
 *
 * @author Maksim Buryshynets
 * @version 1.0 11 March 2019
 */
@Component
class CustomCsrfFilter : OncePerRequestFilter() {

    // TODO: try to replace by spring-bean
    private val accessDeniedHandler = AccessDeniedHandlerImpl()

    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        next: FilterChain
    ) {
        if (!SAFE_METHODS.contains(req.method)) {
            val csrfCookieValue = getCsrfCookieToken(req)
            if ((csrfCookieValue == null) || csrfCookieValue != req.getHeader(CSRF_HEADER_NAME)) {
                accessDeniedHandler.handle(
                    req,
                    res,
                    AccessDeniedException("CSRF token is missing or not matching")
                )
                return
            }
        }
        next.doFilter(req, res)
    }

    private fun getCsrfCookieToken(req: HttpServletRequest): String? {
        if (req.cookies != null) {
            for (cookie in req.cookies) {
                if ((cookie != null) && CSRF_COOKIE_NAME == cookie.name) {
                    return cookie.value
                }
            }
        }
        return null
    }
}
