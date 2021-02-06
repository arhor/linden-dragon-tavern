package org.arhor.diploma.data.persistence.repository

import org.arhor.diploma.data.persistence.domain.SecurityProfile
import org.springframework.stereotype.Repository
import kotlin.reflect.KClass

@Repository
interface SecurityProfileRepository : BaseRepository<SecurityProfile, Long> {

    @JvmDefault
    override val entityType: KClass<SecurityProfile>
        get() = SecurityProfile::class
}
