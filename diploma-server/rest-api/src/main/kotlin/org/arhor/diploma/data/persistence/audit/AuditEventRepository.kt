package org.arhor.diploma.data.persistence.audit

interface AuditEventRepository {

    fun saveAuditEvent(event: AuditEvent, audit: Audit)
}