package org.arhor.diploma.exception

data class EntityNotFoundException(
    val entityName: String = DEFAULT_NAME,
    val propertyName: String = DEFAULT_NAME,
    val propertyValue: Any
) : RuntimeException() {

    companion object {
        const val DEFAULT_NAME = "unknown"
    }
}
