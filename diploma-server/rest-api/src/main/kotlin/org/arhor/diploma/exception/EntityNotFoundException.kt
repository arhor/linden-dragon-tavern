package org.arhor.diploma.exception

private const val UNKNOWN = "unknown"

private val String?.safe: String
    get() = this ?: UNKNOWN

data class EntityNotFoundException(
    val entityType: String?,
    val propName: String?,
    val propValue: Any?
) : RuntimeException("[${entityType.safe}] with property [${propName.safe}]=[${propValue}] is not found")

