package org.arhor.diploma.data.persistence.repository

import kotlinx.coroutines.flow.Flow
import org.arhor.diploma.data.persistence.domain.Authority
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface AuthorityRepository : CoroutineCrudRepository<Authority, Long> {

    @Query(
        """
        SELECT auth.*
          FROM authorities auth
          JOIN security_profile_authorities spa on auth.id = spa.authority_id
         WHERE spa.profile_id = :profileId
        """
    )
    @Transactional(readOnly = true)
    fun findBySecurityProfileId(profileId: Long): Flow<Authority>
}