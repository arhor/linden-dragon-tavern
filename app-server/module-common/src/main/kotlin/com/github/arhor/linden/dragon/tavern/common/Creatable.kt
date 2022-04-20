package com.github.arhor.linden.dragon.tavern.common

import java.time.temporal.TemporalAccessor

interface Creatable<T : TemporalAccessor> {

    var created: T?

    fun onCreate() { /* do nothing by default */ }
}
