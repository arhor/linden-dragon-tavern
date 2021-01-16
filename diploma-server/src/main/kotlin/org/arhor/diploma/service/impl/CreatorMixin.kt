package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Converter
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.arhor.diploma.data.persistence.repository.BaseRepository
import org.arhor.diploma.service.Creator
import java.io.Serializable

class CreatorMixin<
        E : DomainObject<K>,
        D : Identifiable<K>,
        K : Serializable>(
    private val converter: Converter<E, D>,
    private val repository: BaseRepository<E, K>
) : Creator<D, K> {

    override fun create(item: D): D {
        item.id?.let {
            throw IllegalArgumentException("New entity must have $KEY_PROPERTY = null, but passed object has [$it]")
        }

        val newEntity = converter.dtoToEntity(item)
        val savedEntity = repository.save(newEntity)

        return converter.entityToDto(savedEntity)
    }

    companion object {
        const val KEY_PROPERTY = "id"
    }
}
