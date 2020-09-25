package org.arhor.diploma.service.impl

import org.arhor.diploma.core.Identifiable
import org.arhor.diploma.data.persist.domain.core.DomainObject
import org.arhor.diploma.data.persist.repository.BaseRepository
import org.arhor.diploma.service.Creator
import org.arhor.diploma.service.Deleter
import org.arhor.diploma.service.Reader
import org.arhor.diploma.service.Updater
import org.arhor.diploma.exception.EntityNotFoundException
import org.arhor.diploma.service.mapping.Converter
import org.springframework.data.domain.PageRequest
import java.io.Serializable

abstract class AbstractService<E : DomainObject<K>, T : Identifiable<K>, K : Serializable>(
    protected val converter: Converter<E, T>,
    private val repository: BaseRepository<E, K>
) : Creator<T, K>, Reader<T, K>, Updater<T, K>, Deleter<T, K> {

    abstract val type: Class<T>

    override fun create(item: T): T {
        TODO("Not yet implemented")
    }

    override fun getOne(id: K): T {
        return repository
            .findById(id)
            .map { converter.entityToDto(it) }
            .orElseThrow { EntityNotFoundException("account", "id", id) }!!
    }

    override fun getList(): List<T> {
        return repository
            .findAll()
            .mapNotNull { converter.entityToDto(it) }
            .toList()
    }

    override fun getList(page: Int, size: Int): List<T> {
        return repository
            .findAll(PageRequest.of(page, size))
            .toList()
            .mapNotNull { converter.entityToDto(it) }
            .toList()
    }

    override fun update(item: T): T {
        return item.getId()?.let {
            val entity = repository
                .findById(it)
                .orElseThrow { EntityNotFoundException("account", "id", it) }

            val saved = repository.save(entity)

            converter.entityToDto(saved)

        } ?: throw IllegalArgumentException("passed dto has no id")
    }

    override fun delete(id: K) {
        val entity = repository
            .findById(id)
            .orElseThrow { EntityNotFoundException("account", "id", id) }

        repository.delete(entity)
    }

    override fun delete(item: T) {
        item.getId()?.let { delete(it) }
    }
}