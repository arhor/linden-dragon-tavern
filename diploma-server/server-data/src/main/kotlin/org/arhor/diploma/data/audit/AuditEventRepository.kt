package org.arhor.diploma.data.audit

interface AuditEventRepository {

    fun saveAuditEvent(event: AuditEvent, audit: Audit)
}