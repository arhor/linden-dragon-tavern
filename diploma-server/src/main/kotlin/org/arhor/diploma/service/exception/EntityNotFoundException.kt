package org.arhor.diploma.service.exception

data class EntityNotFoundException(
    val className: String,
    val fieldName: String,
    val fieldValue: Any
) : RuntimeException()
