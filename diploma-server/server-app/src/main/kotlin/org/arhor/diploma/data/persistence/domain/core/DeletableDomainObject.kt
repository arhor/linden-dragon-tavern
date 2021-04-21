package org.arhor.diploma.data.persistence.domain.core

import org.arhor.diploma.commons.Deletable
import org.springframework.data.relational.core.mapping.Column
import java.io.Serializable

abstract class DeletableDomainObject<T : Serializable> : DomainObject<T>(),
    Deletable {

    @Column("deleted")
    override var isDeleted: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DeletableDomainObject<*>) return false
        if (!super.equals(other)) return false

        if (isDeleted != other.isDeleted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + isDeleted.hashCode()
        return result
    }
}