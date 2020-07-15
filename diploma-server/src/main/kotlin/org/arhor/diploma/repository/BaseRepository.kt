package org.arhor.diploma.repository

import org.arhor.diploma.domain.core.DomainObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import java.util.*

@NoRepositoryBean
interface BaseRepository<T : DomainObject<K>, K : Serializable> : JpaRepository<T, K> {

    @Transactional(readOnly = true)
    @Query("SELECT e FROM #{#entityName} e WHERE e.isDeleted = false")
    override fun findAll(): MutableList<T>

    @Transactional(readOnly = true)
    @Query("SELECT e FROM #{#entityName} e WHERE e.isDeleted = false")
    override fun findAll(pageable: Pageable): Page<T>

    @Transactional(readOnly = true)
    @Query("SELECT e FROM #{#entityName} e WHERE e.isDeleted = false")
    override fun findAll(sort: Sort): MutableList<T>

    @Transactional(readOnly = true)
    @Query("SELECT e FROM #{#entityName} e WHERE e.id IN :ids AND e.isDeleted = false")
    override fun findAllById(ids: Iterable<K>): MutableList<T>

    @Transactional(readOnly = true)
    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.isDeleted = false")
    override fun findById(id: K): Optional<T>

    @Query("SELECT e FROM #{#entityName} e WHERE e.isDeleted = true")
    @Transactional(readOnly = true)
    fun findDeleted(): MutableList<T>

    @Transactional(readOnly = true)
    @Query("SELECT COUNT(e) FROM #{#entityName} e WHERE e.isDeleted = false")
    override fun count(): Long

    @JvmDefault
    @Transactional(readOnly = true)
    override fun existsById(id: K): Boolean {
        return findById(id).isPresent
    }

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.isDeleted = true WHERE e.id = :id")
    override fun deleteById(id: K)

    @JvmDefault
    @Transactional
    override fun delete(entity: T) {
        entity.id?.let { deleteById(it) }
    }

    @JvmDefault
    @Transactional
    override fun deleteAll(entities: Iterable<T>) {
        deleteAllById(entities.mapNotNull { it.id })
    }

    @JvmDefault
    @Transactional
    override fun deleteInBatch(entities: Iterable<T>) {
        deleteAllById(entities.mapNotNull { it.id })
    }

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.isDeleted = true")
    override fun deleteAll()

    @Modifying
    @Transactional
    @Query("UPDATE #{#entityName} e SET e.isDeleted = true WHERE e.id IN :ids")
    fun deleteAllById(ids: Iterable<K>)
}
