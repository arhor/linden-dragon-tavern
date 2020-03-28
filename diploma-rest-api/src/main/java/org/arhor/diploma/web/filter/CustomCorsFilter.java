package org.arhor.diploma.web.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE;
import static org.springframework.http.HttpHeaders.ORIGIN;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomCorsFilter extends OncePerRequestFilter {

  private static final String ALLOWED_HEADERS = String.join(",",
      "authorization",
      "Content-Type",
      "Authorization",
      "credential",
      "x-csrf-token",
      "x-requested-with"
  );

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest req,
      @NonNull HttpServletResponse res,
      @NonNull FilterChain filterChain) throws ServletException, IOException {

    res.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, req.getHeader(ORIGIN));
    res.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
    res.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "PATCH,POST,PUT,GET,OPTIONS,DELETE");
    res.setHeader(ACCESS_CONTROL_MAX_AGE, "3600");
    res.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);

    if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
      res.setStatus(HttpServletResponse.SC_OK);
    } else {
      filterChain.doFilter(req, res);
    }
  }
}
