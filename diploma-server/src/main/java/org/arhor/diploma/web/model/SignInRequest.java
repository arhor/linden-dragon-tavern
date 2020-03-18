package org.arhor.diploma.web.model;

import lombok.Data;

@Data
public class SignInRequest {
  private String username;
  private String password;
}
