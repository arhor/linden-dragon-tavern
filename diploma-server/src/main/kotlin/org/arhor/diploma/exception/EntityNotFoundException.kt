package org.arhor.diploma.exception

private const val UNKNOWN = "unknown"

data class EntityNotFoundException(
    val entityType: String?,
    val propName: String?,
    val propValue: Any?
) : RuntimeException("[${entityType ?: UNKNOWN}] with property [${propName ?: UNKNOWN}]=[${propValue}] is not found")
