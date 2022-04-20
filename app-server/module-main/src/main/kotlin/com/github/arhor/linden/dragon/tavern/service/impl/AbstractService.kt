package com.github.arhor.linden.dragon.tavern.service.impl

import com.github.arhor.linden.dragon.tavern.common.Converter
import com.github.arhor.linden.dragon.tavern.common.Identifiable
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.core.DeletableDomainObject
import com.github.arhor.linden.dragon.tavern.service.CrudService
import com.github.arhor.linden.dragon.tavern.service.WithCreate
import com.github.arhor.linden.dragon.tavern.service.WithDelete
import com.github.arhor.linden.dragon.tavern.service.WithRead
import com.github.arhor.linden.dragon.tavern.service.WithUpdate
import org.springframework.data.repository.CrudRepository
import java.io.Serializable

abstract class AbstractService<
        E : DeletableDomainObject<K>,
        D : Identifiable<K>,
        K : Serializable>(
    protected val converter: Converter<E, D>,
    private val repository: CrudRepository<E, K>
) : CrudService<E, D, K>,
    WithCreate<E, D, K> by WithCreateMixin(converter, repository),
    WithRead <D, K> by WithReadMixin(converter, repository),
    WithUpdate<D, K> by WithUpdateMixin(converter, repository),
    WithDelete<D, K> by WithDeleteMixin(repository)
