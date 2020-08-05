package org.arhor.diploma.core

import java.time.temporal.TemporalAccessor

interface Creatable<T : TemporalAccessor> {

    var created: T?

    @JvmDefault
    fun onCreate() { /* do nothing by default */ }
}
