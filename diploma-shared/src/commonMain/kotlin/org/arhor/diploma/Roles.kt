package org.arhor.diploma

enum class Roles(val persistent: Boolean = true) {
    USER,
    ADMIN,
    ANONYMOUS(false);

    fun prefixed(): String = "ROLE_${toString()}"
}