package org.arhor.diploma.data.persistence.repository

import kotlinx.coroutines.flow.Flow
import org.arhor.diploma.data.persistence.domain.SecurityProfile
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface SecurityProfileRepository : CoroutineCrudRepository<SecurityProfile, Long> {

    @Transactional(readOnly = true)
    @Query("SELECT p.* FROM security_profiles p WHERE p.name IN (:names)")
    suspend fun findAllByNames(names: List<String>): Flow<SecurityProfile>

    @Transactional(readOnly = true)
    @Query("SELECT p.* FROM security_profiles p WHERE p.name = :name")
    suspend fun findByName(name: String): SecurityProfile?
}
