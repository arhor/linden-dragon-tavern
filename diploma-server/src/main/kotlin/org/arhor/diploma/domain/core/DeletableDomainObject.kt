package org.arhor.diploma.domain.core

import org.arhor.diploma.core.Deletable
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class DeletableDomainObject<T : Serializable> : DomainObject<T>(),
    Deletable {

    @Column(name = "deleted", nullable = false)
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