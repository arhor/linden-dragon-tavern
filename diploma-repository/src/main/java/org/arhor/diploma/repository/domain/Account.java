package org.arhor.diploma.repository.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Account")
@EqualsAndHashCode(callSuper = true)
public class Account extends AuditableDomainObject<Long> {

  @Column(unique = true, nullable = false, length = 30)
  private String username;

  @Column(nullable = false, length = 512)
  private String password;

  @Column(unique = true, length = 128)
  private String email;

  @Column(nullable = false, length = 60)
  private String firstName;

  @Column(nullable = false, length = 60)
  private String lastName;

  @Column(length = 10)
  private String role;
}
