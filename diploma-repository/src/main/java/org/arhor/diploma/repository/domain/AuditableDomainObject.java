package org.arhor.diploma.repository.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class AuditableDomainObject<T> extends DomainObject<T> implements Auditable {

  @Column
  private LocalDateTime created;

  @Column
  private LocalDateTime updated;

  @Override
  @PrePersist
  public void onCreate() {
    setCreated(LocalDateTime.now());
  }

  @Override
  @PreUpdate
  public void onUpdate() {
    setUpdated(LocalDateTime.now());
  }
}
