package org.arhor.diploma.service

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import java.io.Serializable

interface Creator<E, D, K>
        where E : DeletableDomainObject<K>,
              D : Identifiable<K>,
              K : Serializable {

    fun create(item: D, init: E.() -> Unit): D

    /**
     * For Java-interop since Java does not support method argument default values
     */
    @JvmDefault
    fun create(item: D): D {
        return create(item, {})
    }
}