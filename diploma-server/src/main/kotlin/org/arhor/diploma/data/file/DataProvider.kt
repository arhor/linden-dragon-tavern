package org.arhor.diploma.data.file

/**
 * @param T basic data type which provides only few required fields
 * @param D detailed data type which contains all fields
 * @param K identity type
 */
interface DataProvider<T, D, K> {

    fun getOne(id: K): T

    fun getDetails(id: K): D

    fun getList(): List<T>

    fun getList(page: Int, size: Int): List<T>

    fun getDetailsList(): List<D>

    fun getDetailsList(page: Int, size: Int): List<D>
}