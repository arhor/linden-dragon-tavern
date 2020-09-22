package org.arhor.diploma.core

/**
 * General interface for any task which should be executed at application startup stage.
 */
interface StartupTask {

    fun execute()
}