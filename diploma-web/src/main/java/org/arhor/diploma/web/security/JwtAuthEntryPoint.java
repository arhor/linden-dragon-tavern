package org.arhor.diploma.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest req,
      HttpServletResponse res,
      AuthenticationException e) throws IOException {
    log.error("Unauthorized error. Message - {}", e.getMessage());
    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
  }
}
