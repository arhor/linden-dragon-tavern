package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Converter
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import org.arhor.diploma.data.persistence.repository.BaseRepository
import org.arhor.diploma.service.Reader
import org.springframework.data.domain.PageRequest
import java.io.Serializable

class ReaderMixin<E, D, K>(
    private val converter: Converter<E, D>,
    private val repository: BaseRepository<E, K>
) : Reader<D, K>
    where E : DeletableDomainObject<K>,
          D : Identifiable<K>,
          K : Serializable {

    override fun getOne(id: K): D {
        return repository
            .findById(id)
            .map(converter::mapEntityToDto)
            .orElseThrow {
                EntityNotFoundException(
                    entityType = repository.entityType.simpleName,
                    propName = KEY_PROPERTY,
                    propValue = id
                )
            }
    }

    override fun getList(): List<D> {
        return repository
            .findAll()
            .map(converter::mapEntityToDto)
            .toList()
    }

    override fun getList(page: Int, size: Int): List<D> {
        return repository
            .findAll(PageRequest.of(page, size))
            .toList()
            .mapNotNull(converter::mapEntityToDto)
            .toList()
    }

    override fun getTotalSize(): Long {
        return repository.count()
    }

    companion object {
        const val KEY_PROPERTY = "id"
    }
}