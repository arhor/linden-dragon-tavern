package com.github.arhor.linden.dragon.tavern.data.persistence.domain.core

import com.github.arhor.linden.dragon.tavern.common.Auditable
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
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
