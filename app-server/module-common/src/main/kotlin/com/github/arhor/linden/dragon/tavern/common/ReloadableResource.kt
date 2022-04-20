package com.github.arhor.linden.dragon.tavern.common

/**
 * Represents in-memory resource which can be reloaded from its source, for example file.
 */
interface ReloadableResource {

    /**
     * Reloads resource data from its source.
     */
    fun reload()
}
