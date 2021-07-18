package org.arhor.diploma.data.persistence.repository

import kotlinx.coroutines.flow.Flow
import org.arhor.diploma.data.persistence.domain.Account
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : CoroutineCrudRepository<Account, Long> {

    @Query("SELECT a.* FROM ${Account.TABLE_NAME} a WHERE a.deleted = false AND a.username = :username")
    suspend fun findByUsername(username: String): Account?

    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.deleted = true")
    fun findDeleted(): Flow<Account>

    @Query("SELECT e.id FROM ${Account.TABLE_NAME} e WHERE e.deleted = true")
    fun findDeletedIds(): Flow<Long>

    @Modifying
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE id IN (:ids)")
    override suspend fun deleteAllById(ids: Iterable<Long>)

    @Modifying
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE id = :id")
    override suspend fun deleteById(id: Long)

    @Modifying
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE id = :#{#entity.id}")
    override suspend fun delete(entity: Account)

    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.deleted = false")
    override fun findAll(): Flow<Account>

    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.id IN (:ids) AND e.deleted = false")
    override fun findAllById(ids: Iterable<Long>): Flow<Account>

    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.id = :id AND e.deleted = false")
    override suspend fun findById(id: Long): Account?

    @Query("SELECT COUNT(e.id) FROM ${Account.TABLE_NAME} e WHERE e.deleted = false")
    override suspend fun count(): Long

    @Modifying
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE deleted = false")
    override suspend fun deleteAll()

    override suspend fun existsById(id: Long): Boolean {
        return findById(id) != null
    }

    override suspend fun deleteAll(entities: Iterable<Account>) {
        deleteAllById(entities.mapNotNull { it.id })
    }

    suspend fun deleteInBatch(entities: Iterable<Account>) {
        entities.mapNotNull { it.id }
            .takeIf { it.isNotEmpty() }
            ?.let { deleteAllById(it.asIterable()) }
    }
}
