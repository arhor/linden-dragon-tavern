package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Converter
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import org.arhor.diploma.service.*
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.io.Serializable

abstract class AbstractService<
        E : DeletableDomainObject<K>,
        D : Identifiable<K>,
        K : Serializable>(
    protected val converter: Converter<E, D>,
    private val repository: CoroutineCrudRepository<E, K>
) : CrudService<E, D, K>,
    Creator<E, D, K> by CreatorMixin(converter, repository),
    Reader <D, K> by ReaderMixin(converter, repository),
    Updater<D, K> by UpdaterMixin(converter, repository),
    Deleter<D, K> by DeleterMixin(repository)
