package com.github.arhor.linden.dragon.tavern.data.persistence.repository

import com.github.arhor.linden.dragon.tavern.data.persistence.domain.Account
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AccountRepository : CrudRepository<Account, Long> {

    @Query("SELECT a.* FROM ${Account.TABLE_NAME} a WHERE a.deleted = false AND a.username = :username")
    fun findByUsername(username: String): Account?

    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.deleted = true")
    fun findDeleted(): List<Account>

    @Query("SELECT e.id FROM ${Account.TABLE_NAME} e WHERE e.deleted = true")
    fun findDeletedIds(): List<Long>

    @Modifying
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE id IN (:ids)")
    override fun deleteAllById(ids: Iterable<Long>)

    @Modifying
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE id = :id")
    override fun deleteById(id: Long)

    override fun delete(@Param("entity") entity: Account) {
        deleteById(entity.id!!)
    }

    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.deleted = false")
    override fun findAll(): List<Account>

    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.deleted = false LIMIT :size OFFSET :#{#page * #size}")
    fun findAll(page: Int, size: Int): List<Account>

    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.id IN (:ids) AND e.deleted = false")
    override fun findAllById(ids: Iterable<Long>): List<Account>

    @Query("SELECT e.* FROM ${Account.TABLE_NAME} e WHERE e.id = :id AND e.deleted = false")
    override fun findById(id: Long): Optional<Account>

    @Query("SELECT COUNT(e.id) FROM ${Account.TABLE_NAME} e WHERE e.deleted = false")
    override fun count(): Long

    @Modifying
    @Query("UPDATE ${Account.TABLE_NAME} SET deleted = true WHERE deleted = false")
    override fun deleteAll()

    override fun existsById(id: Long): Boolean {
        return findById(id).isPresent
    }

    fun existsByUsername(username: String): Boolean {
        return findByUsername(username) != null
    }

    override fun deleteAll(entities: Iterable<Account>) {
        deleteAllById(entities.mapNotNull { it.id })
    }

    fun deleteInBatch(entities: Iterable<Account>) {
        entities.mapNotNull { it.id }
            .takeIf { it.isNotEmpty() }
            ?.let { deleteAllById(it.asIterable()) }
    }
}
