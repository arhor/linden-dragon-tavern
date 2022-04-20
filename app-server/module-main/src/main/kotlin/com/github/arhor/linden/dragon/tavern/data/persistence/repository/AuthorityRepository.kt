package com.github.arhor.linden.dragon.tavern.data.persistence.repository

import com.github.arhor.linden.dragon.tavern.data.persistence.domain.Authority
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface AuthorityRepository : CrudRepository<Authority, Long> {

    @Query(
        """
        SELECT auth.*
          FROM authorities auth
          JOIN security_profile_authorities spa on auth.id = spa.authority_id
         WHERE spa.profile_id = :profileId
        """
    )
    @Transactional(readOnly = true)
    fun findBySecurityProfileId(profileId: Long): List<Authority>
}
