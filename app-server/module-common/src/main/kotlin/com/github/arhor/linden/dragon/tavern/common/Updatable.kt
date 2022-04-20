package com.github.arhor.linden.dragon.tavern.common

import java.time.temporal.TemporalAccessor

interface Updatable<T : TemporalAccessor> {

    var updated: T?

    fun onUpdate() { /* do nothing by default */ }
}
