package org.arhor.diploma;

import java.time.LocalDateTime;

public interface Auditable
    extends Creatable<LocalDateTime>
          , Updatable<LocalDateTime> {
}
