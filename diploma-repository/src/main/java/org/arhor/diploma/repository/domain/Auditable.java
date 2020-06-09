package org.arhor.diploma.repository.domain;

import java.time.LocalDateTime;

public interface Auditable
    extends Creatable<LocalDateTime>
          , Updatable<LocalDateTime> {
}
