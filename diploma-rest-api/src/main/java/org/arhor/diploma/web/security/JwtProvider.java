package org.arhor.diploma.web.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

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

  public <T> T generateJwtToken(Authentication auth, Function<String, T> converter) {
    var principal = (UserDetails) auth.getPrincipal();
    var payload = objectMapper.createObjectNode();
    var roles = objectMapper.createArrayNode();

    for (var authority : principal.getAuthorities()) {
      roles.add(authority.getAuthority());
    }

    payload.put("email", principal.getUsername());
    payload.set("roles", roles);

    return converter.apply(
        Jwts.builder()
            .setSubject(payload.toString())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expire))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    );
  }

  public String getUserNameFromJwtToken(String token) throws JsonProcessingException {
    final var subject = jwtParser.parseClaimsJws(token)
        .getBody()
        .getSubject();
    return objectMapper.readTree(subject)
        .findValue("email")
        .asText();
  }

  public boolean tokenIsValid(String authToken) {
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
}
