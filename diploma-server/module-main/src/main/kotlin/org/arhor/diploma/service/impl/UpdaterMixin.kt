package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Converter
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import org.arhor.diploma.service.Updater
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.io.Serializable

class UpdaterMixin<E, D, K>(
    private val converter: Converter<E, D>,
    private val repository: CoroutineCrudRepository<E, K>,
) : Updater<D, K>
    where E : DeletableDomainObject<K>,
          D : Identifiable<K>,
          K : Serializable {

    override suspend fun update(item: D): D {
        return item.id?.let {
            return repository.findById(it)
                ?.let { entity -> repository.save(entity) }
                ?.let { saved -> converter.mapEntityToDto(saved) }
                ?: throw EntityNotFoundException(
                    entityType = "IMPLEMENT ME",
                    propName = KEY_PROPERTY,
                    propValue = it
                )
        } ?: throw IllegalArgumentException("Passed item has not set $KEY_PROPERTY value, so it cannot be updated")
    }

    companion object {
        const val KEY_PROPERTY = "id"
    }
}