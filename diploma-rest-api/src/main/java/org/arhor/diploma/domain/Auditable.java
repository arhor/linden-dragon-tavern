package org.arhor.diploma.domain;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public interface Auditable
    extends Creatable<LocalDateTime>
          , Updatable<LocalDateTime> {

  @Override
  @PrePersist
  default void onCreate() {
    setCreated(LocalDateTime.now());

  }

  @Override
  @PreUpdate
  default void onUpdate() {
    setUpdated(LocalDateTime.now());
  }
}
