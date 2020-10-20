package org.arhor.diploma.commons

interface Verifiable : Comparable<Verifiable> {

    val priority: Int

    fun verify(): ActionResult<String>

    @JvmDefault
    override operator fun compareTo(other: Verifiable): Int {
        return priority.compareTo(other.priority)
    }
}