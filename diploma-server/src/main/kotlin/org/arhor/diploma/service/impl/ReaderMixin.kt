package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.arhor.diploma.data.persistence.repository.BaseRepository
import org.arhor.diploma.exception.EntityNotFoundException
import org.arhor.diploma.service.Reader
import org.arhor.diploma.commons.Converter
import org.springframework.data.domain.PageRequest
import java.io.Serializable

class ReaderMixin<
        E : DomainObject<K>,
        D : Identifiable<K>,
        K : Serializable>(
    private val converter: Converter<E, D>,
    private val repository: BaseRepository<E, K>
) : Reader<D, K> {

    override fun getOne(id: K): D {
        return repository
            .findById(id)
            .map { converter.entityToDto(it) }
            .orElseThrow {
                EntityNotFoundException(
                    entityType = repository.getEntityName(),
                    propertyName = KEY_PROPERTY,
                    propertyValue = id
                )
            }
    }

    override fun getList(): List<D> {
        return repository
            .findAll()
            .map(converter::entityToDto)
            .toList()
    }

    override fun getList(page: Int, size: Int): List<D> {
        return repository
            .findAll(PageRequest.of(page, size))
            .toList()
            .mapNotNull(converter::entityToDto)
            .toList()
    }

    override fun getTotalSize(): Long {
        return repository.count()
    }

    companion object {
        const val KEY_PROPERTY = "id"
    }
}