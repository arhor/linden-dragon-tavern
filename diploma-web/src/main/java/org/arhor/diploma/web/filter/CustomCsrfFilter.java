package org.arhor.diploma.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
  * Class CustomCsrfFilter implements stateless CSRF protection. To successfully
  * pass a CSRF check, the request must contain the CSRF token generated on the
  * client side in the appropriate header and cookie, and the token in the header
  * must match the token in the cookie.
  *
  * @author Maksim Buryshynets
  * @version 1.0 11 March 2019
  */
@Slf4j
@Component
public class CustomCsrfFilter extends OncePerRequestFilter {

  private static final String CSRF_COOKIE_NAME = "csrf-token";
  private static final String CSRF_HEADER_NAME = "x-csrf-token";

  private final AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();
  private final Set<String> safeMethods = Set.of("GET", "HEAD", "TRACE", "OPTIONS");

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest req,
      @NonNull HttpServletResponse res,
      @NonNull FilterChain filterChain) throws IOException, ServletException {

    final var method = req.getMethod();

    if (!safeMethods.contains(method)) {
      log.debug("validating CSRF request");

      final var csrfCookieValue = getCsrfCookieToken(req);

      log.debug("CSRF cookie value: {}", csrfCookieValue);

      if ((csrfCookieValue == null) || !csrfCookieValue.equals(req.getHeader(CSRF_HEADER_NAME))) {
        accessDeniedHandler.handle(
            req,
            res,
            new AccessDeniedException("CSRF token is missing or not matching"));
        return;
      }
    }
    filterChain.doFilter(req, res);
  }

  private String getCsrfCookieToken(HttpServletRequest req) {
    final Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (Cookie c : cookies) {
        if ((c != null) && CSRF_COOKIE_NAME.equals(c.getName())) {
          return c.getValue();
        }
      }
    }
    return null;
  }
}
