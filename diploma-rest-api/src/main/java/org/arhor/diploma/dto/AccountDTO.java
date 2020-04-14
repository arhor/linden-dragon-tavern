package org.arhor.diploma.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public final class AccountDTO {
  private Long id;
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String role;
  private LocalDateTime created;
  private LocalDateTime updated;
  private boolean deleted;
}
