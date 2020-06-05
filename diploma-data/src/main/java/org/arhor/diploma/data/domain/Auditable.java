package org.arhor.diploma.data.domain;

import java.time.LocalDateTime;

public interface Auditable
    extends Creatable<LocalDateTime>
          , Updatable<LocalDateTime> {
}
