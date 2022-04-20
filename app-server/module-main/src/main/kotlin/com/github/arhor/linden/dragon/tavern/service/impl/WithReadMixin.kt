package com.github.arhor.linden.dragon.tavern.service.impl

import com.github.arhor.linden.dragon.tavern.common.Converter
import com.github.arhor.linden.dragon.tavern.common.Identifiable
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.core.DeletableDomainObject
import com.github.arhor.linden.dragon.tavern.exception.EntityNotFoundException
import com.github.arhor.linden.dragon.tavern.service.WithRead
import org.springframework.data.repository.CrudRepository
import java.io.Serializable

class WithReadMixin<E, D, K>(
    private val converter: Converter<E, D>,
    private val repository: CrudRepository<E, K>
) : WithRead<D, K>
    where E : DeletableDomainObject<K>,
          D : Identifiable<K>,
          K : Serializable {

    override fun getOne(id: K): D {
        return repository
            .findById(id)
            .map(converter::mapEntityToDto)
            .orElseThrow {
                EntityNotFoundException(
                    "IMPLEMENT ME",
                    "$KEY_PROPERTY = $id",
                )
            }
    }

    override fun getList(): List<D> {
        return repository.findAll().map(converter::mapEntityToDto)
    }

    override fun getList(page: Int, size: Int): List<D> {
        TODO("to be implemented")
    }

    override fun getTotalSize(): Long {
        return repository.count()
    }

    companion object {
        const val KEY_PROPERTY = "id"
    }
}
