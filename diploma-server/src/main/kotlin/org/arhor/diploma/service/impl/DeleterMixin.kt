package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import org.arhor.diploma.data.persistence.repository.BaseRepository
import org.arhor.diploma.exception.EntityNotFoundException
import org.arhor.diploma.service.Deleter
import java.io.Serializable

class DeleterMixin<E, D, K>(
    private val repository: BaseRepository<E, K>,
) : Deleter<D, K>
        where E : DeletableDomainObject<K>,
              D : Identifiable<K>,
              K : Serializable {

    override fun delete(id: K) {
        val entity = repository
            .findById(id)
            .orElseThrow {
                EntityNotFoundException(
                    entityType = repository.entityType.simpleName,
                    propName = UpdaterMixin.KEY_PROPERTY,
                    propValue = id
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