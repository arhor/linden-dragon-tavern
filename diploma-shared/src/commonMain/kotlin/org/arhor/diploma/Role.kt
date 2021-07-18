package org.arhor.diploma

enum class Role(val persistent: Boolean = true) {
    USER,
    ADMIN,
    ANONYMOUS(false),
    ;

    fun prefixed(): String = "ROLE_${toString()}"
}