package org.arhor.diploma.domain.core

import java.time.temporal.TemporalAccessor

interface Creatable<T : TemporalAccessor> {

  var created: T?

  @JvmDefault
  fun onCreate() { /* do nothing by default */ }
}
