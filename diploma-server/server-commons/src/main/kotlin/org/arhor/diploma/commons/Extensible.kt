package org.arhor.diploma.commons

interface Extensible {

    /**
     * List of the
     */
    val names: Set<String>

    operator fun get(name: String): Any?

    operator fun set(name: String, value: Any?)

    fun remove(name: String)

    fun clear()
}