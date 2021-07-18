package org.arhor.diploma.data.persistence.domain.core

import org.arhor.diploma.commons.Auditable
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import java.io.Serializable
import java.time.LocalDateTime

abstract class AuditableDomainObject<T> : DeletableDomainObject<T>(), Auditable
    where T : Serializable {

    @CreatedDate
    override var created: LocalDateTime? = null

    @LastModifiedDate
    override var updated: LocalDateTime? = null

    override fun onCreate() {
        created = LocalDateTime.now()
    }

    override fun onUpdate() {
        updated = LocalDateTime.now()
    }

    fun copyBaseState(domainObject: AuditableDomainObject<T>) {
        this.isDeleted = domainObject.isDeleted
        this.created = domainObject.created
        this.updated = domainObject.updated
    }
}
