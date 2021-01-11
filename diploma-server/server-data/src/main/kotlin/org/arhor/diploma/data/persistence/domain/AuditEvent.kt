package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.STATIC_HASH_CODE
import org.arhor.diploma.data.persistence.domain.core.DomainObject
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "audit_events")
class AuditEvent : DomainObject<Long>() {

    @Column(nullable = false)
    var table: String? = null

    @Column(nullable = false)
    var type: Type? = null

    @Column(nullable = false)
    var entityId: Number? = null

    @Column(nullable = false)
    var principal: String? = null

    @Column(nullable = false)
    var timestamp: LocalDateTime? = null

    @OneToMany(mappedBy = "auditEvent", cascade = [CascadeType.ALL], orphanRemoval = true)
    var data: List<AuditData> = mutableListOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuditEvent) return false
        if (!super.equals(other)) return false

        if (table != other.table) return false
        if (type != other.type) return false
        if (entityId != other.entityId) return false
        if (principal != other.principal) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        return STATIC_HASH_CODE
    }

    override fun toString(): String {
        return "AuditEvent(table=$table, type=$type, entityId=$entityId, principal=$principal, timestamp=$timestamp)"
    }

    enum class Type {
        CREATE, UPDATE, DELETE;
    }
}