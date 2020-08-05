package org.arhor.diploma.core

import java.time.temporal.TemporalAccessor

interface Updatable<T : TemporalAccessor> {

    var updated: T?

    @JvmDefault
    fun onUpdate() { /* do nothing by default */ }
}
