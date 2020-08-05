package org.arhor.diploma.service.impl

import org.arhor.diploma.domain.core.DomainObject
import org.arhor.diploma.repository.BaseRepository
import org.arhor.diploma.service.Creator
import org.arhor.diploma.service.Deleter
import org.arhor.diploma.service.Reader
import org.arhor.diploma.service.Updater
import org.arhor.diploma.service.dto.DTO
import org.arhor.diploma.service.exception.EntityNotFoundException
import org.arhor.diploma.util.Converter
import org.springframework.data.domain.PageRequest
import java.io.Serializable

abstract class AbstractService<E : DomainObject<K>, D : DTO<K>, K : Serializable>(
    private val converter: Converter,
    private val repository: BaseRepository<E, K>
) : Creator<D, K>, Reader<D, K>, Updater<D, K>, Deleter<D, K> {

    abstract val type: Class<D>

    override fun create(dto: D): D {
        TODO("Not yet implemented")
    }

    override fun getOne(id: K): D {
        return repository
            .findById(id)
            .map { converter.convert(it, type) }
            .orElseThrow { EntityNotFoundException("account", "id", id) }
    }

    override fun getList(): List<D> {
        return repository
            .findAll()
            .map { converter.convert(it, type) }
            .toList()
    }

    override fun getList(page: Int, size: Int): List<D> {
        return repository
            .findAll(PageRequest.of(page, size))
            .map { converter.convert(it, type) }
            .toList()
    }

    override fun update(dto: D): D {
        return dto.id?.let {
            val entity = repository
                .findById(it)
                .orElseThrow { EntityNotFoundException("account", "id", it) }

            val saved = repository.save(entity)

            converter.convert(saved, type)

        } ?: throw IllegalArgumentException("passed dto has no id")
    }

    override fun delete(id: K) {
        val entity = repository
            .findById(id)
            .orElseThrow { EntityNotFoundException("account", "id", id) }

        repository.delete(entity)
    }

    override fun delete(dto: D) {
        dto.id?.let { delete(it) }
    }


    internal inline fun <reified D> Any.convertTo(): D {
        return converter.convert(this, D::class.java)
    }
}