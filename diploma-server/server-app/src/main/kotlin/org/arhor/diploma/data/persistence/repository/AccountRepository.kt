package org.arhor.diploma.data.persistence.repository

import org.arhor.diploma.data.Cache
import org.arhor.diploma.data.persistence.domain.Account
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.reflect.KClass

@Repository
interface AccountRepository : PagingAndSortingRepository<Account, Long> {

    @JvmDefault
    val entityType: KClass<Account>
        get() = Account::class

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = [Cache.Names.ACCOUNT_BY_USERNAME], key = "#username")
    @Query("SELECT a FROM accounts a WHERE a.deleted = false AND a.username = :username")
    fun findByUsername(username: String): Optional<Account>

    @Modifying
    @Transactional
    @CacheEvict(cacheNames = [Cache.Names.ACCOUNT_BY_ID], key = "#id")
    @Query("UPDATE accounts SET deleted = true WHERE id = :id")
    override fun deleteById(id: Long)

    @JvmDefault
    @Transactional
    @CacheEvict(cacheNames = [Cache.Names.ACCOUNT_BY_USERNAME], key = "#entity.username")
    override fun delete(entity: Account) {
        entity.id?.let { deleteById(it) }
    }
}
