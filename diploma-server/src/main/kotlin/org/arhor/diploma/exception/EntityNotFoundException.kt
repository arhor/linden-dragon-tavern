package org.arhor.diploma.exception

data class EntityNotFoundException(
    val className: String = "unknown",
    val fieldName: String = "unknown",
    val fieldValue: Any
) : RuntimeException()
