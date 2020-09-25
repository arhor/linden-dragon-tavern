package org.arhor.diploma.exception

data class EntityNotFoundException(
    val className: String,
    val fieldName: String,
    val fieldValue: Any
) : RuntimeException()
