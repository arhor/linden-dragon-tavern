package org.arhor.diploma.domain

import java.time.LocalDateTime

interface Auditable : Creatable<LocalDateTime>
                    , Updatable<LocalDateTime>
