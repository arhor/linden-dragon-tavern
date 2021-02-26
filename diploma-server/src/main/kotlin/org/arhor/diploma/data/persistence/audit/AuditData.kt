package org.arhor.diploma.data.persistence.audit

import org.arhor.diploma.data.persistence.domain.core.DomainObject
import javax.persistence.*


class AuditData : DomainObject<Long>() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    override var id: Long? = null

    @Column(nullable = false)
    var property: String? = null

    @Column
    var oldValue: String? = null

    @Column
    var newValue: String? = null

    @ManyToOne(optional = false)
    @MapsId("audit_event_id")
    var auditEvent: AuditEvent? = null

    override val tableName: String
        get() = TODO("Not yet implemented")
}