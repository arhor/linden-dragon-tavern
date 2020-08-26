package org.arhor.diploma.data.persist.repository

import org.arhor.diploma.data.persist.domain.SecurityProfile
import org.springframework.stereotype.Repository

@Repository
interface SecurityProfileRepository : BaseRepository<SecurityProfile, Long>
