package org.arhor.diploma.web.filter;

import org.arhor.commons.pattern.lazy.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
  * Class CustomCsrfFilter implements stateless CSRF protection. To successfully
  * pass a CSRF check, the request must contain the CSRF token generated on the
  * client side in the appropriate header and cookie, and the token in the header
  * must match the token in the cookie.
  *
  * @author Maksim Buryshynets
  * @version 1.0 11 March 2019
  */
public class CustomCsrfFilter extends OncePerRequestFilter {

  private static final String ERROR_MSG = "CSRF token is missing or not matching";
  private static final String CSRF_COOKIE = "csrf-token";
  private static final String CSRF_HEADER = "x-csrf-token";

  private final AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();
  private final Supplier<Pattern> safeMethod = Lazy.evalSafe(() -> Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$"));

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest req,
      @NonNull HttpServletResponse res,
      @NonNull FilterChain filterChain) throws IOException, ServletException {

    final var method = req.getMethod();
    final var isTokenRequired = safeMethod.get().matcher(method).matches();

    if (isTokenRequired) {
      final var csrfHeaderToken = req.getHeader(CSRF_HEADER);
      final var csrfCookieToken = getCsrfCookieToken(req);

      if ((csrfCookieToken == null) || !csrfCookieToken.equals(csrfHeaderToken)) {
        accessDeniedHandler.handle(req, res, new AccessDeniedException(ERROR_MSG));
      }
    }
    filterChain.doFilter(req, res);
  }

  private String getCsrfCookieToken(HttpServletRequest req) {
    final var cookies = req.getCookies();
    if (cookies != null) {
      for (var cookie : cookies) {
        if ((cookie != null) && CSRF_COOKIE.equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }
}
