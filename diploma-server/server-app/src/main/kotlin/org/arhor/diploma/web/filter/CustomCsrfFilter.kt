//package org.arhor.diploma.web.filter
//
//import org.arhor.diploma.CsrfUtils.CSRF_COOKIE_NAME
//import org.arhor.diploma.CsrfUtils.CSRF_HEADER_NAME
//import org.arhor.diploma.CsrfUtils.SAFE_METHODS
//import org.arhor.diploma.exception.CustomCsrfException
//import org.arhor.diploma.exception.InvalidCsrfTokenException
//import org.arhor.diploma.exception.MissingCsrfTokenException
//import org.springframework.security.web.access.AccessDeniedHandlerImpl
//import org.springframework.web.filter.OncePerRequestFilter
//import javax.servlet.FilterChain
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//
///**
// * Class CustomCsrfFilter implements stateless CSRF protection. To successfully
// * pass a CSRF check, the request must contain the CSRF token generated on the
// * client side in the appropriate header and cookie, and the token in the header
// * must match the token in the cookie.
// *
// * @author Maksim Buryshynets
// * @version 1.0 11 March 2019
// */
//class CustomCsrfFilter : OncePerRequestFilter() {
//
//    private val accessDeniedHandler = AccessDeniedHandlerImpl()
//
//    override fun doFilterInternal(
//        req: HttpServletRequest,
//        res: HttpServletResponse,
//        next: FilterChain
//    ) {
//        if (!SAFE_METHODS.contains(req.method.toUpperCase())) {
//            val csrfCookieValue = getCsrfCookieToken(req)
//            val csrfHeaderValue = req.getHeader(CSRF_HEADER_NAME)
//
//            validate(csrfCookieValue, csrfHeaderValue)?.let { csrfException ->
//                accessDeniedHandler.handle(req, res, csrfException)
//                return
//            }
//        }
//        next.doFilter(req, res)
//    }
//
//    private fun getCsrfCookieToken(req: HttpServletRequest): String? {
//        if (req.cookies != null) {
//            for (cookie in req.cookies) {
//                if ((cookie != null) && CSRF_COOKIE_NAME == cookie.name) {
//                    return cookie.value
//                }
//            }
//        }
//        return null
//    }
//
//    private fun validate(csrfCookie: String?, csrfHeader: String?): CustomCsrfException? {
//        return when {
//            csrfCookie == null || csrfHeader == null -> {
//                MissingCsrfTokenException()
//            }
//            csrfCookie != csrfHeader -> {
//                InvalidCsrfTokenException(expected = csrfCookie, actual = csrfHeader)
//            }
//            else -> {
//                null
//            }
//        }
//    }
//}
