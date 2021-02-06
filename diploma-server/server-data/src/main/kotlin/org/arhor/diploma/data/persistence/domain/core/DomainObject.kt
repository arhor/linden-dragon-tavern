package org.arhor.diploma.data.persistence.domain.core

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.extension.ObjectExtensionLoader
import org.arhor.diploma.data.persistence.domain.extension.ExtraDataHolder
import java.io.Serializable
import javax.persistence.*

/**
 * Base class for any entity used in the application.
 *
 * @param T primary key type
 */
@MappedSuperclass
@EntityListeners(ObjectExtensionLoader::class)
abstract class DomainObject<T : Serializable> : Identifiable<T>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    override var id: T? = null

    @Transient
    final val extraData: ExtraDataHolder = ExtraDataHolder()

    abstract val tableName: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DomainObject<*>) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    companion object {
        /**
         * Sequence generator name base to be used in subclasses in pair with
         * [javax.persistence.SequenceGenerator] annotation to define database
         * primary key sequence name for the concrete entity.
         */
        const val SEQ_GEN_NAME = "SEQ_GEN"

        /**
         * Number of pre-allocated keys by the sequence generator:
         * - in cases when there is only one database client it can be greater then 1
         * - in cases when there are multiple database clients it MUST be set to 1
         */
        const val SEQ_ALLOC_SIZE = 25
    }
}
