package org.arhor.diploma.commons

import java.io.Serializable

interface MutableIdentity<T : Serializable>: Identifiable<T> {

    fun setId(id: T?)
}