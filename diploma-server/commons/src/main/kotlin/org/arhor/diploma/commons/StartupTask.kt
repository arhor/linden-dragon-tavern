package org.arhor.diploma.commons

/**
 * General interface for any task which should be executed at application startup stage.
 */
interface StartupTask {

    fun execute()
}