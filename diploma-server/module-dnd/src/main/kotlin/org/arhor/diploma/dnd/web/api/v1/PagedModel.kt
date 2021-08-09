package org.arhor.diploma.dnd.web.api.v1

data class PagedModel<out T>(
    val items: List<T>,
    val total: Long,
)