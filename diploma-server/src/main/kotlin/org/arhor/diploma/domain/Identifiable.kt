package org.arhor.diploma.domain

import java.io.Serializable

interface Identifiable<T : Serializable> {

  fun getId(): T?

  fun setId(id: T?)
}
