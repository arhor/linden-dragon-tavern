package org.arhor.diploma.service

import org.arhor.diploma.service.dto.DTO
import java.io.Serializable

interface Creator<D : DTO<K>, K : Serializable> {

    fun create(dto: D): D
}