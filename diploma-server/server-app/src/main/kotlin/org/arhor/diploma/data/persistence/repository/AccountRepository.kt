package org.arhor.diploma.data.persistence.repository

import kotlinx.coroutines.flow.Flow
import org.arhor.diploma.data.persistence.domain.Account
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Suppress("unused")
interface AccountRepository : CoroutineCrudRepository<Account, Long> {

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = [Account.CACHE_BY_USERNAME], key = "#username")
    @Query("SELECT a.* FROM ${Account.TABLE_NAME} a WHERE a.deleted = false AND a.username = :username")
    suspend fun findByUsername(username: String): Account?

    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.deleted = true")
    @Transactional(readOnly = true)
    fun findDeleted(): Flow<Account>

    @Query("SELECT e.id FROM ${Account.TABLE_NAME} e WHERE e.deleted = true")
    @Transactional(readOnly = true)
    fun findDeletedIds(): Flow<Long>

    @JvmDefault
    @Transactional
    suspend fun deleteInBatch(entities: Iterable<Account>) {
        deleteAllById(entities.mapNotNull { it.id })
    }

    @Modifying
    @Transactional
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE id IN (:ids)")
    suspend fun deleteAllById(ids: Iterable<Long>)

    @Modifying
    @Transactional
    @CacheEvict(cacheNames = [Account.CACHE_BY_ID], key = "#id")
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE id = :id")
    override suspend fun deleteById(id: Long)

    @JvmDefault
    @Transactional
    @CacheEvict(cacheNames = [Account.CACHE_BY_USERNAME], key = "#entity.username")
    override suspend fun delete(entity: Account) {
        entity.id?.let { deleteById(it) }
    }

    @Transactional(readOnly = true)
    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.deleted = false")
    override fun findAll(): Flow<Account>

    @Transactional(readOnly = true)
    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.id IN (:ids) AND e.deleted = false")
    override fun findAllById(ids: Iterable<Long>): Flow<Account>

    @Transactional(readOnly = true)
    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.id = :id AND e.deleted = false")
    override suspend fun findById(id: Long): Account?

    @Transactional(readOnly = true)
    @Query("SELECT COUNT(e.id) FROM ${Account.TABLE_NAME} e WHERE e.deleted = false")
    override suspend fun count(): Long

    @JvmDefault
    @Transactional(readOnly = true)
    override suspend fun existsById(id: Long): Boolean {
        return findById(id) != null
    }

    @JvmDefault
    @Transactional
    override suspend fun deleteAll(entities: Iterable<Account>) {
        deleteAllById(entities.mapNotNull { it.id })
    }

    @Modifying
    @Transactional
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE true")
    override suspend fun deleteAll()
}
