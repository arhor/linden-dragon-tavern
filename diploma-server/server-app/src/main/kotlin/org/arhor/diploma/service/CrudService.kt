package org.arhor.diploma.service

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import java.io.Serializable

interface CrudService<E, D, K>
    : Creator<E, D, K>, Reader<D, K>, Updater<D, K>, Deleter<D, K>
        where E : DeletableDomainObject<K>,
              D : Identifiable<K>,
              K : Serializable