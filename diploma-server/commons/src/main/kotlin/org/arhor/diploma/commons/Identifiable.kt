package org.arhor.diploma.commons

import java.io.Serializable

interface Identifiable<T : Serializable> {

    fun getId(): T?
}
