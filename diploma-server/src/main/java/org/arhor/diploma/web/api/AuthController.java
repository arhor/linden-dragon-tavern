package org.arhor.diploma.web.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arhor.diploma.web.model.JwtResponse;
import org.arhor.diploma.web.model.SignInRequest;
import org.arhor.diploma.web.security.JwtProvider;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(
    path = "/auth",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

  private final AuthenticationManager authManager;
  private final JwtProvider jwtProvider;

  @PostMapping("/token")
  public JwtResponse authenticate(@RequestBody SignInRequest signIn) {
    var auth = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            signIn.getUsername(),
            signIn.getPassword()
        )
    );

    SecurityContextHolder
        .getContext()
        .setAuthentication(auth);

    return jwtProvider.generateJwtToken(auth, JwtResponse::of);
  }

  @PostMapping("/register")
  public JwtResponse register() {
    return null;
  }

  @GetMapping("/refresh")
  @PreAuthorize("isAuthenticated()")
  public JwtResponse refresh(Authentication auth) {
    return jwtProvider.generateJwtToken(auth, JwtResponse::of);
  }
}
