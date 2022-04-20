package com.github.arhor.linden.dragon.tavern.common

fun <T> Collection<T>.chunk(page: Int, size: Int): Collection<T> {
    return this.drop((page - 1) * size).take(size)
}
