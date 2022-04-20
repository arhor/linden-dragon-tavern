package com.github.arhor.linden.dragon.tavern.service.impl

import com.github.arhor.linden.dragon.tavern.common.Converter
import com.github.arhor.linden.dragon.tavern.common.Identifiable
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.core.DeletableDomainObject
import com.github.arhor.linden.dragon.tavern.exception.EntityNotFoundException
import com.github.arhor.linden.dragon.tavern.service.WithUpdate
import org.springframework.data.repository.CrudRepository
import java.io.Serializable

class WithUpdateMixin<E, D, K>(
    private val converter: Converter<E, D>,
    private val repository: CrudRepository<E, K>,
) : WithUpdate<D, K>
    where E : DeletableDomainObject<K>,
          D : Identifiable<K>,
          K : Serializable {

    override fun update(item: D): D {
        return item.id?.let {
            return repository.findById(it)
                .map { entity -> repository.save(entity) }
                .map { saved -> converter.mapEntityToDto(saved) }
                .orElseThrow {
                    EntityNotFoundException(
                        "IMPLEMENT ME",
                        "$KEY_PROPERTY = $it",
                    )
                }
        } ?: throw IllegalArgumentException("Passed item has not set $KEY_PROPERTY value, so it cannot be updated")
    }

    companion object {
        const val KEY_PROPERTY = "id"
    }
}
