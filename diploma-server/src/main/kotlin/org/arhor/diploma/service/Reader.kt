package org.arhor.diploma.service

import org.arhor.diploma.core.Identifiable
import java.io.Serializable

interface Reader<T : Identifiable<K>, K : Serializable> {

    fun getOne(id: K): T

    fun getList(): List<T>

    fun getList(page: Int, size: Int): List<T>
}