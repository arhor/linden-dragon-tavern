package org.arhor.diploma.exception

data class EntityNotFoundException(
    val entityType: String = UNKNOWN,
    val propertyName: String = UNKNOWN,
    val propertyValue: Any
) : RuntimeException("[${entityType}] with property [${propertyName}] = [${propertyValue}] is not found") {

    companion object {
        const val UNKNOWN = "unknown"
    }
}
