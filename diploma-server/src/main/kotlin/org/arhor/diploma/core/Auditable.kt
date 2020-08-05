package org.arhor.diploma.core

import java.time.LocalDateTime

interface Auditable : Creatable<LocalDateTime>,
    Updatable<LocalDateTime>
