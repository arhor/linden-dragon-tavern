package org.arhor.diploma.domain.core

import org.arhor.diploma.core.Identifiable
import java.io.Serializable
import javax.persistence.*

@MappedSuperclass
abstract class DomainObject<T : Serializable> : Identifiable<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: T? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DomainObject<*>) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
