package org.arhor.diploma.core

import java.io.Serializable

interface Identifiable<T : Serializable> {

    fun getId(): T?
}
