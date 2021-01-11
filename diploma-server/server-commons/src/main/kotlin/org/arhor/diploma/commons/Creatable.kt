package org.arhor.diploma.commons

import java.time.temporal.TemporalAccessor

interface Creatable<T : TemporalAccessor> {

    var created: T?

    @JvmDefault
    fun onCreate() { /* do nothing by default */ }
}
