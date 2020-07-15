package org.arhor.diploma.domain.core

import java.io.Serializable

interface Identifiable<T : Serializable> {

    var id: T?
}
