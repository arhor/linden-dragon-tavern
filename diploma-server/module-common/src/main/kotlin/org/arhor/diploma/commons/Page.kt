package org.arhor.diploma.commons

data class Page<T>(
    val items: Collection<T>,
    val total: Int = items.size
)
