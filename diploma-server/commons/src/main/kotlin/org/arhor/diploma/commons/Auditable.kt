package org.arhor.diploma.commons

import java.time.LocalDateTime

interface Auditable : Creatable<LocalDateTime>, Updatable<LocalDateTime>
