package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persist.domain.core.DomainObject
import org.arhor.diploma.data.persist.repository.BaseRepository
import org.arhor.diploma.exception.EntityNotFoundException
import org.arhor.diploma.service.Updater
import org.arhor.diploma.commons.Converter
import java.io.Serializable

class UpdaterMixin<
        E : DomainObject<K>,
        D : Identifiable<K>,
        K : Serializable>(
    private val converter: Converter<E, D>,
    private val repository: BaseRepository<E, K>
) : Updater<D, K> {

    override fun update(item: D): D {
        return item.getId()?.let {
            val entity = repository
                .findById(it)
                .orElseThrow {
                    EntityNotFoundException(
                        entityType = repository.getEntityName(),
                        propertyName = KEY_PROPERTY,
                        propertyValue = it
                    )
                }

            val saved = repository.save(entity)

            return converter.entityToDto(saved)

        } ?: throw IllegalArgumentException("Passed item has not set $KEY_PROPERTY value, so it cannot be updated")
    }

    companion object {
        const val KEY_PROPERTY = "id"
    }
}