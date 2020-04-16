package org.arhor.diploma.web.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arhor.diploma.service.AccountService;
import org.arhor.diploma.web.security.JwtProvider;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthTokenFilter extends OncePerRequestFilter {

  private final JwtProvider tokenProvider;
  private final AccountService accountService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest req,
      @NonNull HttpServletResponse res,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    try {
      final String jwt = extractJwt(req);

      if (jwt != null && tokenProvider.tokenIsValid(jwt)) {
        final String username = tokenProvider.getUsernameFromJwtToken(jwt);

        final var userDetails = accountService.loadUserByUsername(username);

        // if (SecurityContextHolder.getContext().getAuthentication() == null) { ... }

        final var auth =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (Exception e) {
      log.error("Can NOT set user authentication -> Message: {}", e.getMessage());
    }

    filterChain.doFilter(req, res);
  }

  private String extractJwt(HttpServletRequest request) {
    final String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.replace("Bearer ", "");
    }

    return null;
  }
}
