package org.arhor.diploma.data.persistence.repository

import org.arhor.diploma.data.persistence.domain.Account
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.reflect.KClass

@Repository
@Suppress("unused")
interface AccountRepository : PagingAndSortingRepository<Account, Long> {

    @JvmDefault
    val entityType: KClass<Account>
        get() = Account::class

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = [Account.CACHE_BY_USERNAME], key = "#username")
    @Query("SELECT a.* FROM ${Account.TABLE_NAME} a WHERE a.deleted = false AND a.username = :username")
    fun findByUsername(username: String): Optional<Account>

    @Modifying
    @Transactional
    @CacheEvict(cacheNames = [Account.CACHE_BY_ID], key = "#id")
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE id = :id")
    override fun deleteById(id: Long)

    @JvmDefault
    @Transactional
    @CacheEvict(cacheNames = [Account.CACHE_BY_USERNAME], key = "#account.username")
    override fun delete(account: Account) {
        account.id?.let { deleteById(it) }
    }

    @Transactional(readOnly = true)
    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.deleted = false")
    override fun findAll(): MutableList<Account>

    @Transactional(readOnly = true)
    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.deleted = false")
    override fun findAll(pageable: Pageable): Page<Account>

    @Transactional(readOnly = true)
    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.deleted = false")
    override fun findAll(sort: Sort): MutableList<Account>

    @Transactional(readOnly = true)
    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.id IN (:ids) AND e.deleted = false")
    override fun findAllById(ids: Iterable<Long>): MutableList<Account>

    @Transactional(readOnly = true)
    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.id = :id AND e.deleted = false")
    override fun findById(id: Long): Optional<Account>

    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.deleted = true")
    @Transactional(readOnly = true)
    fun findDeleted(): MutableList<Account>

    @Query("SELECT e.id FROM ${Account.TABLE_NAME} e WHERE e.deleted = true")
    @Transactional(readOnly = true)
    fun findDeletedIds(): MutableList<Long>

    @Transactional(readOnly = true)
    @Query("SELECT COUNT(e.id) FROM ${Account.TABLE_NAME} e WHERE e.deleted = false")
    override fun count(): Long

    @JvmDefault
    @Transactional(readOnly = true)
    override fun existsById(id: Long): Boolean {
        return findById(id).isPresent
    }

    @JvmDefault
    @Transactional
    override fun deleteAll(entities: Iterable<Account>) {
        deleteAllById(entities.mapNotNull { it.id })
    }

    @JvmDefault
    @Transactional
    fun deleteInBatch(entities: Iterable<Account>) {
        deleteAllById(entities.mapNotNull { it.id })
    }

    @Modifying
    @Transactional
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE true")
    override fun deleteAll()

    @Modifying
    @Transactional
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE id IN (:ids)")
    fun deleteAllById(ids: Iterable<Long>)
}
