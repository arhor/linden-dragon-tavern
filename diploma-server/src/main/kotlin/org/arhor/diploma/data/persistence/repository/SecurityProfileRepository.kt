package org.arhor.diploma.data.persistence.repository

import org.arhor.diploma.data.persistence.domain.SecurityProfile
import org.springframework.stereotype.Repository

@Repository
interface SecurityProfileRepository : BaseRepository<SecurityProfile, Long> {
    @JvmDefault
    override fun getEntityName(): String = "SecurityProfile"
}
