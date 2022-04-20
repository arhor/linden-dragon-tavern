package com.github.arhor.linden.dragon.tavern.common

import java.time.LocalDateTime

interface Auditable : Creatable<LocalDateTime>, Updatable<LocalDateTime>
