package org.arhor.diploma.service

import org.arhor.diploma.commons.Identifiable
import java.io.Serializable

interface Deleter<T : Identifiable<K>, K : Serializable> {

    suspend fun delete(id: K)

    suspend fun delete(item: T)
}