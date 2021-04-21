package org.arhor.diploma.data.persistence.repository

import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import java.util.*
import kotlin.reflect.KClass

@Suppress("unused")
@NoRepositoryBean
interface BaseRepository<T, K> : PagingAndSortingRepository<T, K>
        where T : DeletableDomainObject<K>,
              K : Serializable {

    val entityType: KClass<T>

    val tableName: String

    @Transactional(readOnly = true)
    @Query("SELECT e.* FROM #{#entityName} e WHERE e.deleted = false")
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

    @Query("SELECT e.id FROM #{#entityName} e WHERE e.isDeleted = true")
    @Transactional(readOnly = true)
    fun findDeletedIds(): MutableList<K>

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
        entity.id?.let(::deleteById)
    }

    @JvmDefault
    @Transactional
    override fun deleteAll(entities: Iterable<T>) {
        deleteAllById(entities.mapNotNull { it.id })
    }

    @JvmDefault
    @Transactional
    fun deleteInBatch(entities: Iterable<T>) {
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
