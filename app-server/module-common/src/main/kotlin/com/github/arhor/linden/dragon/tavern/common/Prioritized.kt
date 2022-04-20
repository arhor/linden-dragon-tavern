package com.github.arhor.linden.dragon.tavern.common

interface Prioritized<T> : Comparable<Prioritized<T>> {

    val priority: Priority

    override operator fun compareTo(other: Prioritized<T>): Int {
        return priority.value.compareTo(other.priority.value)
    }
}
