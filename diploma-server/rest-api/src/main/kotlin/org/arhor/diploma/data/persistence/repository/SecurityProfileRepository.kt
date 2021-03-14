package org.arhor.diploma.data.persistence.repository

import org.arhor.diploma.data.persistence.domain.SecurityProfile
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.reflect.KClass

@Repository
interface SecurityProfileRepository : BaseRepository<SecurityProfile, Long> {

    @JvmDefault
    override val entityType: KClass<SecurityProfile>
        get() = SecurityProfile::class

    @Transactional(readOnly = true)
    @Query("SELECT p FROM SecurityProfile p WHERE p.name IN :names")
    fun findAllByNames(names: List<String>): List<SecurityProfile>

    @Transactional(readOnly = true)
    @Query("SELECT p FROM SecurityProfile p WHERE p.name = :name")
    fun findByName(name: String): Optional<SecurityProfile>

    @JvmDefault
    fun findByNameOrNull(name: String): SecurityProfile? {
        return findByName(name).orElse(null)
    }
}
