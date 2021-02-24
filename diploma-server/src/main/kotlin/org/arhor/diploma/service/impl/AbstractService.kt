package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Converter
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DeletableDomainObject
import org.arhor.diploma.data.persistence.repository.BaseRepository
import org.arhor.diploma.service.*
import java.io.Serializable

abstract class AbstractService<
        E : DeletableDomainObject<K>,
        D : Identifiable<K>,
        K : Serializable>(
    protected val converter: Converter<E, D>,
    private val repository: BaseRepository<E, K>
) : CrudService<D, K>,
    Creator<D, K> by CreatorMixin(converter, repository),
    Reader <D, K> by ReaderMixin(converter, repository),
    Updater<D, K> by UpdaterMixin(converter, repository),
    Deleter<D, K> by DeleterMixin(repository)
