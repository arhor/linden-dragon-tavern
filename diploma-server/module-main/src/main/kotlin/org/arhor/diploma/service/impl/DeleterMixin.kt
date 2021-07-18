package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import org.arhor.diploma.service.Deleter
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.io.Serializable

class DeleterMixin<E, D, K>(
    private val repository: CoroutineCrudRepository<E, K>,
) : Deleter<D, K>
    where E : DeletableDomainObject<K>,
          D : Identifiable<K>,
          K : Serializable {

    override suspend fun delete(id: K) {
        val entity = repository.findById(id)

        if (entity != null) {
            repository.deleteById(id)
        } else {
            throw EntityNotFoundException(
                entityType = "IMPLEMENT ME",
                propName = KEY_PROPERTY,
                propValue = id
            )
        }
    }

    override suspend fun delete(item: D) {
        item.id?.let { delete(it) }
            ?: throw IllegalArgumentException("Passed item has not set $KEY_PROPERTY value, so it cannot be deleted")
    }

    companion object {
        const val KEY_PROPERTY = "id"
    }
}