package org.arhor.diploma.data.persistence.repository

import org.arhor.diploma.data.persistence.DisableAudit
import org.arhor.diploma.data.persistence.domain.AuditEvent
import org.springframework.stereotype.Repository

@Repository
@DisableAudit
interface AuditEventRepository : BaseRepository<AuditEvent, Long> {

    @JvmDefault
    override fun getEntityName(): String = "AuditEvent"
}