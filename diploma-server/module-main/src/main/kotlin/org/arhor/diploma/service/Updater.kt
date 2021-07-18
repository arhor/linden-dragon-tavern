package org.arhor.diploma.service

import org.arhor.diploma.commons.Identifiable
import java.io.Serializable

interface Updater<T : Identifiable<K>, K : Serializable> {

    suspend fun update(item: T): T
}