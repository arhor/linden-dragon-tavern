package com.github.arhor.linden.dragon.tavern.data.persistence.repository

import com.github.arhor.linden.dragon.tavern.Role
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.SecurityProfile
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface SecurityProfileRepository : CrudRepository<SecurityProfile, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT p.* FROM security_profiles p WHERE p.name IN (:names)")
    fun findAllByNames(names: List<String>): List<SecurityProfile>

    @Transactional(readOnly = true)
    @Query("SELECT p.* FROM security_profiles p WHERE p.name = :#{#role.name}")
    fun findByRole(role: Role): SecurityProfile?

    @Transactional(readOnly = true)
    @Query(
        """
        SELECT p.*
          FROM security_profiles p
          JOIN account_details d ON p.id = d.profile_id
          JOIN accounts a ON d.username = a.username
         WHERE a.id = :accountId
        """
    )
    fun findByAccountId(accountId: Long): SecurityProfile?
}
