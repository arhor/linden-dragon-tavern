package org.arhor.diploma.commons.data

private val String?.safe: String
    get() = this ?: "unknown"

sealed class DataAccessException(message: String?) : RuntimeException(message)

data class EntityNotFoundException(
    val entityType: String?,
    val propName: String?,
    val propValue: Any?
) : DataAccessException(
    message = "[${entityType.safe}] with property [${propName.safe}]=[${propValue}] is not found"
)