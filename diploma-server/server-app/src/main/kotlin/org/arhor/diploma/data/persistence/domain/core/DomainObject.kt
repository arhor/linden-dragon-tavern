package org.arhor.diploma.data.persistence.domain.core

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.extension.ExtraDataHolder
import org.arhor.diploma.data.persistence.domain.extension.ObjectExtensionLoader
import java.io.Serializable
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import javax.persistence.Transient

/**
 * Base class for any entity used in the application.
 *
 * @param T primary key type
 */
@MappedSuperclass
@EntityListeners(ObjectExtensionLoader::class)
abstract class DomainObject<T : Serializable> : Identifiable<T>, Serializable {

    @Transient
    final val extraData: ExtraDataHolder = ExtraDataHolder()

    abstract val tableName: String

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
        const val SEQ_ALLOC_SIZE = 1
    }
}
