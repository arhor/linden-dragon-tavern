package org.arhor.diploma.service.impl

import org.arhor.diploma.core.Identifiable
import org.arhor.diploma.data.persist.domain.core.DomainObject
import org.arhor.diploma.data.persist.repository.BaseRepository
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
            .orElseThrow { EntityNotFoundException("account", "id", id) }

        repository.delete(entity)
    }

    override fun delete(item: D) {
        item.getId()?.let { delete(it) }
    }
}