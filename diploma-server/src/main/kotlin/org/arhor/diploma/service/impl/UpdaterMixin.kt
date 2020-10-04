package org.arhor.diploma.service.impl

import org.arhor.diploma.core.Identifiable
import org.arhor.diploma.data.persist.domain.core.DomainObject
import org.arhor.diploma.data.persist.repository.BaseRepository
import org.arhor.diploma.exception.EntityNotFoundException
import org.arhor.diploma.service.Updater
import org.arhor.diploma.service.mapping.Converter
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
                .orElseThrow { EntityNotFoundException("account", "id", it) }

            val saved = repository.save(entity)

            converter.entityToDto(saved)

        } ?: throw IllegalArgumentException("passed dto has no id")
    }
}