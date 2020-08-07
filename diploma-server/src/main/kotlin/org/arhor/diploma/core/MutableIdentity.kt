package org.arhor.diploma.core

import java.io.Serializable

interface MutableIdentity<T : Serializable>: Identifiable<T> {

    fun setId(id: T?)
}