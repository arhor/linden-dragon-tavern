package org.arhor.diploma.web.security;

import java.util.Optional;

public interface TokenProvider<A> {

  String generate(A auth);

  Optional<String> parse(String token);

  Optional<String> parseUsername(String token) throws java.io.IOException;

  boolean validate(String token);
}
