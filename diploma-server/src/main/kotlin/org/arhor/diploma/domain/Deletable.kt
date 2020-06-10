package org.arhor.diploma.domain

interface Deletable {

  fun isDeleted(): Boolean

  fun setDeleted(isDeleted: Boolean)

  @JvmDefault
  fun onDelete() { /* do nothing by default */ }
}
