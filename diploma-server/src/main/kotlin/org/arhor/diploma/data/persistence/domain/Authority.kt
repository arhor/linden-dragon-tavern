package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.arhor.diploma.util.Common.STATIC_HASH_CODE
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "authorities")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
class Authority : DomainObject<Long>() {

    @Column(unique = true, nullable = false)
    var name: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Authority) return false
        if (!super.equals(other)) return false
        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        return STATIC_HASH_CODE
    }

    override fun toString(): String {
        return "Authority(name=$name)"
    }
}