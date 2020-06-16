package org.arhor.diploma.domain.core

import java.time.LocalDateTime

interface Auditable : Creatable<LocalDateTime>
                    , Updatable<LocalDateTime>
