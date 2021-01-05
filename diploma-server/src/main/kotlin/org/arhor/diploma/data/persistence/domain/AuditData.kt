package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.persistence.domain.core.DomainObject
import javax.persistence.*

@Entity
@Table(name = "audit_data")
class AuditData : DomainObject<Long>() {

    @Column(nullable = false)
    var property: String? = null

    @Column
    var oldValue: String? = null

    @Column
    var newValue: String? = null

    @ManyToOne(optional = false)
    @MapsId("audit_event_id")
    var auditEvent: AuditEvent? = null
}