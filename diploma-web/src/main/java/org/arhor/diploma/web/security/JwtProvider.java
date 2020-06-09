package org.arhor.diploma.web.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arhor.diploma.web.model.JwtResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.arhor.diploma.web.util.CustomCollectors.toArrayNode;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider implements TokenProvider<Authentication> {

  public static final String TOKEN_TYPE = "Bearer";

  private static final String FIELD_USERNAME = "username";
  private static final String FIELD_ROLES = "roles";

  private final ObjectMapper objectMapper;

  @Value("${security.jwt.secret}")
  private String secret;

  @Value("${security.jwt.expire}")
  private int expire;

  private JwtParser jwtParser;

  @PostConstruct
  public void init() {
    jwtParser = Jwts.parser().setSigningKey(secret);
  }

  @Override
  public String generate(Authentication auth) {
    return Jwts.builder()
        .setSubject(asJsonString(auth::getPrincipal))
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expire))
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  @Override
  public Optional<String> parse(String token) {
    if (token != null) {
      return Optional.of(
          token.startsWith(JwtProvider.TOKEN_TYPE)
              ? token.replace(JwtProvider.TOKEN_TYPE, "").trim()
              : token);
    }
    return Optional.empty();
  }

  @Override
  public Optional<String> parseUsername(String token) throws JsonProcessingException {
    final var subject = jwtParser.parseClaimsJws(token).getBody().getSubject();

    return subject != null
        ? Optional.ofNullable(objectMapper.readTree(subject).findValue(FIELD_USERNAME).asText())
        : Optional.empty();
  }

  @Override
  public boolean validate(String authToken) {
    try {
      return jwtParser.parseClaimsJws(authToken)
          .getBody()
          .getExpiration()
          .before(new Date());
    } catch (SignatureException e) {
      log.error("Invalid JWT signature -> Message: {} ", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token -> Message: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token -> Message: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token -> Message: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty -> Message: {}", e.getMessage());
    }
    return false;
  }

  private String asJsonString(Supplier<?> principalSource) {
    final var principal = (UserDetails) principalSource.get();

    final var roles =
        principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(toArrayNode(objectMapper));

    return objectMapper.createObjectNode()
        .put(FIELD_USERNAME, principal.getUsername())
        .set(FIELD_ROLES, roles)
        .toString();
  }
}
