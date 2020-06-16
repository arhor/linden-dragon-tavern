package org.arhor.diploma.domain.core

interface Deletable {

  var isDeleted: Boolean

  @JvmDefault
  fun onDelete() { /* do nothing by default */ }
}
