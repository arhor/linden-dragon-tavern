package com.github.arhor.linden.dragon.tavern.service.impl

import com.github.arhor.linden.dragon.tavern.common.Converter
import com.github.arhor.linden.dragon.tavern.common.Identifiable
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.core.DeletableDomainObject
import com.github.arhor.linden.dragon.tavern.service.WithCreate
import org.springframework.data.repository.CrudRepository
import java.io.Serializable

class WithCreateMixin<E, D, K>(
    private val converter: Converter<E, D>,
    private val repository: CrudRepository<E, K>
) : WithCreate<E, D, K>
    where E : DeletableDomainObject<K>,
          D : Identifiable<K>,
          K : Serializable {

    override fun create(item: D): D {
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
