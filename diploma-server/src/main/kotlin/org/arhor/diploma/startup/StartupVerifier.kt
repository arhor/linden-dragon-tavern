package org.arhor.diploma.startup

import org.arhor.diploma.util.ActionResult

interface StartupVerifier : Comparable<StartupVerifier> {

    val order: Int

    fun verify(): ActionResult<String>

    @JvmDefault
    override operator fun compareTo(other: StartupVerifier): Int {
        return order.compareTo(other.order)
    }
}