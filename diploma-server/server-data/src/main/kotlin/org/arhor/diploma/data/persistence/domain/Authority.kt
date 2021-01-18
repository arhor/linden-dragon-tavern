package org.arhor.diploma.data.persistence.domain

import org.arhor.diploma.data.STATIC_HASH_CODE
import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name = "authorities")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SequenceGenerator(
    name = DomainObject.SEQ_GEN_NAME,
    sequenceName = "authorities_id_seq",
    allocationSize = DomainObject.SEQ_ALLOC_SIZE
)
class Authority : DomainObject<Long>() {

    override val tableName: String
        get() = "authorities"

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