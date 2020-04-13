package org.arhor.diploma.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.arhor.diploma.Auditable;
import org.arhor.diploma.Deletable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "User")
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity<Long> implements Auditable, Deletable {

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

  @Column(nullable = false, length = 10)
  private String role;

  @Column(nullable = false)
  private LocalDateTime created;

  @Column(nullable = false)
  private LocalDateTime updated;

  @Column(nullable = false)
  private boolean deleted;

  @PrePersist
  public void onCreate() {
    created = LocalDateTime.now();
  }

  @PreUpdate
  public void onUpdate() {
    updated = LocalDateTime.now();
  }
}
