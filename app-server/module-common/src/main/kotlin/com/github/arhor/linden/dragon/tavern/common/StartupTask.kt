package com.github.arhor.linden.dragon.tavern.common

/**
 * General interface for any task which should be executed at application startup stage.
 */
interface StartupTask : Prioritized<StartupTask> {

    fun execute()
}
