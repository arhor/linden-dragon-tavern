package org.arhor.diploma.commons

import java.io.Serializable

interface Identifiable<T : Serializable> {

    val id: T?
}
