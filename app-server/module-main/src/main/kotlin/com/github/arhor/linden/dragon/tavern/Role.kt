package com.github.arhor.linden.dragon.tavern

enum class Role(val persistent: Boolean = true) {
    USER,
    ADMIN,
    ANONYMOUS(false),
    ;

    val prefixed: String
        get() = "ROLE_$name"
}
