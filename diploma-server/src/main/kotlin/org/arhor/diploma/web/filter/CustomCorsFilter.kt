package org.arhor.diploma.web.filter

import org.arhor.diploma.aspect.LoggingAspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import  org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS
import  org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS
import  org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS
import  org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN
import  org.springframework.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE
import  org.springframework.http.HttpHeaders.ORIGIN

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CustomCorsFilter : OncePerRequestFilter() {

  companion object {
    @JvmStatic
    private val log: Logger = LoggerFactory.getLogger(LoggingAspect::class.java)

    @JvmStatic
    private val ALLOWED_HEADERS = arrayOf(
        "authorization",
        "Content-Type",
        "Authorization",
        "credential",
        "x-csrf-token",
        "x-requested-with"
    ).joinToString(separator = ",")

    @JvmStatic
    private val ALLOWED_METHODS = arrayOf(
        "GET",
        "POST",
        "PUT",
        "PATCH",
        "OPTIONS",
        "DELETE"
    ).joinToString(separator = ",")

    private const val MAX_AGE = 3600.toString()
  }

  override fun doFilterInternal(
      req: HttpServletRequest,
      res: HttpServletResponse,
      next: FilterChain
  ) {
    res.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, req.getHeader(ORIGIN))
    res.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, true.toString())
    res.setHeader(ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS)
    res.setHeader(ACCESS_CONTROL_MAX_AGE, MAX_AGE)
    res.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS)

    if ("OPTIONS".equals(req.method, ignoreCase = true)) {
      res.status = HttpServletResponse.SC_OK
    } else {
      next.doFilter(req, res)
    }
  }
}
