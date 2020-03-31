package org.arhor.diploma.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tblUsers")
@EqualsAndHashCode(callSuper = true)
public class User extends DeletableAbstractEntity<Long> {
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String role;
}
