package org.arhor.diploma.commons

interface Prioritized<T> : Comparable<Prioritized<T>> {

    val priority: Priority

    @JvmDefault
    override operator fun compareTo(other: Prioritized<T>): Int {
        return priority.value.compareTo(other.priority.value)
    }
}