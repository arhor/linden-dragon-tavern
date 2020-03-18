package org.arhor.diploma.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity<Long> {
  private String username;
  private String password;
  private String email;

  public static final User SUPER_ADMIN;
  static {
    final var goddess = new User();
    goddess.setId(Long.MIN_VALUE);
    goddess.setUsername("");
    goddess.setPassword("");
    SUPER_ADMIN = goddess;
  }
}
