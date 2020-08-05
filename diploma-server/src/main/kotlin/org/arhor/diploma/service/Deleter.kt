package org.arhor.diploma.service

import org.arhor.diploma.service.dto.DTO
import java.io.Serializable

interface Deleter<D : DTO<K>, K : Serializable> {

    fun delete(id: K)

    fun delete(dto: D)
}