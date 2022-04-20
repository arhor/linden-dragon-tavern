package com.github.arhor.linden.dragon.tavern.common

data class Page<T>(
    val items: Collection<T>,
    val total: Int = items.size
)
