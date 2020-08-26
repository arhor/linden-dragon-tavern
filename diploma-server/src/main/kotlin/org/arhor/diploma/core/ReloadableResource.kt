package org.arhor.diploma.core

/**
 * Represents in-memory resource which can be reloaded from its source, for example file.
 */
interface ReloadableResource {

    /**
     * Reloads resource data from its source.
     */
    fun reload()
}