package org.arhor.diploma.web.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class JwtResponse {
  String accessToken;
  String tokenType;
}
