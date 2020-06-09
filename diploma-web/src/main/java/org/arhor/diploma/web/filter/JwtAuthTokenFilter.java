package org.arhor.diploma.web.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arhor.diploma.service.AccountService;
import org.arhor.diploma.web.security.TokenProvider;
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
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthTokenFilter extends OncePerRequestFilter {

  private final TokenProvider<?> tokenProvider;
  private final AccountService accountService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest req,
      @NonNull HttpServletResponse res,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    try {
      final String authHeader = req.getHeader("Authentication");
      final Optional<String> jwt = tokenProvider.parse(authHeader);

      if (jwt.isPresent() && tokenProvider.validate(jwt.get())) {
        tokenProvider
            .parseUsername(jwt.get())
            .map(accountService::loadUserByUsername)
            .ifPresent(userDetails -> {
              final var auth =
                  new UsernamePasswordAuthenticationToken(
                      userDetails,
                      null,
                      userDetails.getAuthorities());

              auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

              SecurityContextHolder.getContext().setAuthentication(auth);
            });
      }
    } catch (Exception e) {
      log.error("Can NOT set user authentication -> Message: {}", e.getMessage());
    }

    filterChain.doFilter(req, res);
  }
}
