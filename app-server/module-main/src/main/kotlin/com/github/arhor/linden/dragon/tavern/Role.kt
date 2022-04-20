package com.github.arhor.linden.dragon.tavern

enum class Role(val persistent: Boolean = true) {
    USER,
    ADMIN,
    ANONYMOUS(false),
    ;

    fun prefixed(): String = "ROLE_${toString()}"
}
