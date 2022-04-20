package com.github.arhor.linden.dragon.tavern.service.impl

import com.github.arhor.linden.dragon.tavern.common.Identifiable
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.core.DeletableDomainObject
import com.github.arhor.linden.dragon.tavern.exception.EntityNotFoundException
import com.github.arhor.linden.dragon.tavern.service.WithDelete
import org.springframework.data.repository.CrudRepository
import java.io.Serializable

class WithDeleteMixin<E, D, K>(
    private val repository: CrudRepository<E, K>,
) : WithDelete<D, K>
    where E : DeletableDomainObject<K>,
          D : Identifiable<K>,
          K : Serializable {

    override fun delete(id: K) {
        val entity = repository.findById(id).orElseThrow {
            EntityNotFoundException(
                "IMPLEMENT ME",
                "$KEY_PROPERTY = $id",
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
