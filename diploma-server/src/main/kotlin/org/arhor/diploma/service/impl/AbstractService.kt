package org.arhor.diploma.service.impl

import org.arhor.diploma.domain.core.DomainObject
import org.arhor.diploma.repository.BaseRepository
import org.arhor.diploma.service.exception.EntityNotFoundException
import org.arhor.diploma.util.Converter
import org.springframework.data.domain.PageRequest
import java.io.Serializable

abstract class AbstractService<E : DomainObject<K>, D, K : Serializable>(
    private val converter: Converter,
    private val repository: BaseRepository<E, K>
) {

    internal inline fun <reified D> getOne(id: K): D {
        return repository
            .findById(id)
            .map { it.convertTo<D>() }
            .orElseThrow { EntityNotFoundException("account", "id", id) }
    }

    internal inline fun <reified D> getList(page: Int, size: Int): List<D> {
        return repository
            .findAll(PageRequest.of(page, size))
            .map { it.convertTo<D>() }
            .toList()
    }

    internal inline fun <reified D> Any.convertTo(): D {
        return converter.convert(this, D::class.java)
    }
}