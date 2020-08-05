package org.arhor.diploma.service

import org.arhor.diploma.service.dto.DTO
import java.io.Serializable

interface Updater<D : DTO<K>, K : Serializable> {

    fun update(dto: D): D
}