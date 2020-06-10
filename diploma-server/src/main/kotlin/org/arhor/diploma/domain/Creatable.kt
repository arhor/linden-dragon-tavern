package org.arhor.diploma.domain

import java.time.temporal.TemporalAccessor

interface Creatable<T : TemporalAccessor> {

  fun getCreated(): T?

  fun setCreated(created: T?)

  @JvmDefault
  fun onCreate() { /* do nothing by default */ }
}
