package org.arhor.diploma.repository

import org.arhor.diploma.domain.Account
import org.arhor.diploma.util.Cache
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface AccountRepository : BaseRepository<Account, Long> {

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = [Cache.Names.ACCOUNT_BY_USERNAME], key = "#username")
    @Query("SELECT a FROM Account a WHERE a.isDeleted = false AND a.username = :username")
    fun findByUsername(username: String): Optional<Account>

    @Modifying
    @Transactional
    @CacheEvict(cacheNames = [Cache.Names.ACCOUNT_BY_ID], key = "#id")
    @Query("UPDATE Account a SET a.isDeleted = true WHERE a.id = :id")
    override fun deleteById(id: Long)

    @JvmDefault
    @Transactional
    @CacheEvict(cacheNames = [Cache.Names.ACCOUNT_BY_USERNAME], key = "#entity.username")
    override fun delete(entity: Account) {
        entity.id?.let { deleteById(it) }
    }
}
