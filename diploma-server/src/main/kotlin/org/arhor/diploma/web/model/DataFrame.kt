package org.arhor.diploma.web.model

data class DataFrame<T>(
    val meta: MetaInfo,
    val data: List<T>
)