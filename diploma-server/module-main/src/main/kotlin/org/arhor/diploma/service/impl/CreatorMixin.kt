package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Converter
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import org.arhor.diploma.service.Creator
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.io.Serializable

class CreatorMixin<E, D, K>(
    private val converter: Converter<E, D>,
    private val repository: CoroutineCrudRepository<E, K>
) : Creator<E, D, K>
        where E : DeletableDomainObject<K>,
              D : Identifiable<K>,
              K : Serializable {

    override suspend fun create(item: D): D {
        if (item.id != null) {
            throw IllegalArgumentException(
                "New entity must have $KEY_PROPERTY = null, but passed object has [${item.id}]"
            )
        }
        return converter.mapDtoToEntity(item)
            .let { repository.save(it) }
            .let(converter::mapEntityToDto)
    }

    companion object {
        const val KEY_PROPERTY = "id"
    }
}
