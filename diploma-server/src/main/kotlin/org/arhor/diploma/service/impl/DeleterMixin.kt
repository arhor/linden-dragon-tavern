package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.arhor.diploma.data.persistence.repository.BaseRepository
import org.arhor.diploma.exception.EntityNotFoundException
import org.arhor.diploma.service.Deleter
import java.io.Serializable

class DeleterMixin<
        E : DomainObject<K>,
        D : Identifiable<K>,
        K : Serializable>(
    private val repository: BaseRepository<E, K>
) : Deleter<D, K> {

    override fun delete(id: K) {
        val entity = repository
            .findById(id)
            .orElseThrow {
                EntityNotFoundException(
                    entityType = repository.getEntityName(),
                    propertyName = UpdaterMixin.KEY_PROPERTY,
                    propertyValue = id
                )
            }

        repository.delete(entity)
    }

    override fun delete(item: D) {
        item.id?.let { delete(it) }
            ?: throw IllegalArgumentException("Passed item has not set $KEY_PROPERTY value, so it cannot be deleted")
    }

    companion object {
        const val KEY_PROPERTY = "id"
    }
}