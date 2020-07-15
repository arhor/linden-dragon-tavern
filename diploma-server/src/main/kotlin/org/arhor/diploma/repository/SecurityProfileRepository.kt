package org.arhor.diploma.repository

import org.arhor.diploma.domain.SecurityProfile
import org.springframework.stereotype.Repository

@Repository
interface SecurityProfileRepository : BaseRepository<SecurityProfile, Long>
