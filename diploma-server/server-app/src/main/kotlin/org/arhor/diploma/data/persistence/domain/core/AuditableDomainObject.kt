package org.arhor.diploma.data.persistence.domain.core

import org.arhor.diploma.commons.Auditable
import org.springframework.data.relational.core.mapping.Column
import java.io.Serializable
import java.time.LocalDateTime

abstract class AuditableDomainObject<T> : DeletableDomainObject<T>(), Auditable
        where T : Serializable {

    @Column
    override var created: LocalDateTime? = null

    @Column
    override var updated: LocalDateTime? = null

    override fun onCreate() {
        created = LocalDateTime.now()
    }

    override fun onUpdate() {
        updated = LocalDateTime.now()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuditableDomainObject<*>) return false
        if (!super.equals(other)) return false

        if (created != other.created) return false
        if (updated != other.updated) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (created?.hashCode() ?: 0)
        result = 31 * result + (updated?.hashCode() ?: 0)
        return result
    }
}
