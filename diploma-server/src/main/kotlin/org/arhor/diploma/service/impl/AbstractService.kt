package org.arhor.diploma.service.impl

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.persistence.domain.core.DomainObject
import org.arhor.diploma.data.persistence.repository.BaseRepository
import org.arhor.diploma.service.Creator
import org.arhor.diploma.service.Deleter
import org.arhor.diploma.service.Reader
import org.arhor.diploma.service.Updater
import org.arhor.diploma.commons.Converter
import java.io.Serializable

abstract class AbstractService<
        E : DomainObject<K>,
        D : Identifiable<K>,
        K : Serializable>(
    protected val converter: Converter<E, D>,
    private val repository: BaseRepository<E, K>
) : Creator<D, K> by CreatorMixin(converter, repository),
    Reader <D, K> by ReaderMixin(converter, repository),
    Updater<D, K> by UpdaterMixin(converter, repository),
    Deleter<D, K> by DeleterMixin(repository) {
}