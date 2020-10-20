package org.arhor.diploma.service

import org.arhor.diploma.commons.Identifiable
import java.io.Serializable

interface Creator<T : Identifiable<K>, K : Serializable> {

    fun create(item: T): T
}