package org.arhor.diploma.service

import org.arhor.diploma.service.dto.DTO
import java.io.Serializable

interface Reader<D : DTO<K>, K : Serializable> {

    fun getOne(id: K): D

    fun getList(): List<D>

    fun getList(page: Int, size: Int): List<D>
}