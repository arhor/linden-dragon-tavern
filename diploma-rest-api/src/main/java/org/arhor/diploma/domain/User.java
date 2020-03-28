package org.arhor.diploma.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends DeletableAbstractEntity<Long> {
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String role;
}
