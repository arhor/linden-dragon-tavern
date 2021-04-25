package org.arhor.diploma.service

import kotlinx.coroutines.flow.Flow
import org.arhor.diploma.commons.Identifiable
import java.io.Serializable

interface Reader<T : Identifiable<K>, K : Serializable> {

    suspend fun getOne(id: K): T

    fun getList(): Flow<T>

    fun getList(page: Int, size: Int): Flow<T>

    suspend fun getTotalSize(): Long
}