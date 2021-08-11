package org.arhor.diploma.dnd.data.repository

import org.arhor.diploma.commons.Page

/**
 * @param T basic data type which provides only few required fields
 * @param K identity type
 */
interface DataRepository<T, K> {

    fun count(): Int

    fun count(query: (T) -> Boolean): Int

    fun findById(id: K): T

    fun getPage(): Page<T>

    fun getPage(page: Int, size: Int): Page<T>

    fun getPage(query: (T) -> Boolean): Page<T>

    fun getPage(page: Int, size: Int, query: (T) -> Boolean = { true }): Page<T>
}