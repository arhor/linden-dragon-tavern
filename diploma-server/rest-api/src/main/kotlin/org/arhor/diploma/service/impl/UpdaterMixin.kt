package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Converter
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import org.arhor.diploma.data.persistence.repository.BaseRepository
import org.arhor.diploma.exception.EntityNotFoundException
import org.arhor.diploma.service.Updater
import java.io.Serializable

class UpdaterMixin<E, D, K>(
    private val converter: Converter<E, D>,
    private val repository: BaseRepository<E, K>,
) : Updater<D, K>
        where E : DeletableDomainObject<K>,
              D : Identifiable<K>,
              K : Serializable {

    override fun update(item: D): D {
        return item.id?.let {
            val entity = repository
                .findById(it)
                .orElseThrow {
                    EntityNotFoundException(
                        entityType = repository.entityType.simpleName,
                        propName = KEY_PROPERTY,
                        propValue = it
                    )
                }

            val saved = repository.save(entity)

            return converter.mapEntityToDto(saved)

        } ?: throw IllegalArgumentException("Passed item has not set $KEY_PROPERTY value, so it cannot be updated")
    }

    companion object {
        const val KEY_PROPERTY = "id"
    }
}