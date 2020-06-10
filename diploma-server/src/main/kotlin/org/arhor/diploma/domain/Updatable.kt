package org.arhor.diploma.domain

import java.time.temporal.TemporalAccessor

interface Updatable<T : TemporalAccessor> {

  fun getUpdated(): T?

  fun setUpdated(updated: T?)

  @JvmDefault
  fun onUpdate() { /* do nothing by default */ }
}
