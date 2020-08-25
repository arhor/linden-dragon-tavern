package org.arhor.diploma.service.data

interface DataProvider<T, D, K> {

    fun getOne(id: K): T

    fun getDetails(id: K): D

    fun getList(): List<T>

    fun getList(page: Int, size: Int): List<T>

    fun getDetailsList(): List<D>

    fun getDetailsList(page: Int, size: Int): List<D>
}