package org.arhor.diploma.domain;

import java.time.LocalDateTime;

public interface Auditable
    extends Creatable<LocalDateTime>
          , Updatable<LocalDateTime> {
}
