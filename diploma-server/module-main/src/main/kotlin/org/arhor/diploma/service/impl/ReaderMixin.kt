package org.arhor.diploma.service.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.arhor.diploma.commons.Converter
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import org.arhor.diploma.service.Reader
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.io.Serializable

class ReaderMixin<E, D, K>(
    private val converter: Converter<E, D>,
    private val repository: CoroutineCrudRepository<E, K>
) : Reader<D, K>
    where E : DeletableDomainObject<K>,
          D : Identifiable<K>,
          K : Serializable {

    override suspend fun getOne(id: K): D {
        return repository
            .findById(id)
            ?.let(converter::mapEntityToDto)
            ?: throw
                EntityNotFoundException(
                    entityType = "IMPLEMENT ME",
                    propName = KEY_PROPERTY,
                    propValue = id
                )
    }

    override fun getList(): Flow<D> {
        return repository.findAll().map(converter::mapEntityToDto)
    }

    override fun getList(page: Int, size: Int): Flow<D> {
        TODO("to be implemented")
    }

    override suspend fun getTotalSize(): Long {
        return repository.count()
    }

    companion object {
        const val KEY_PROPERTY = "id"
    }
}