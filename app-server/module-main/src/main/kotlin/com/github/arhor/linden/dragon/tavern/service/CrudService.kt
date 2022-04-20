package com.github.arhor.linden.dragon.tavern.service

import com.github.arhor.linden.dragon.tavern.common.Identifiable
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.core.DeletableDomainObject
import java.io.Serializable

interface CrudService<E, D, K> :
    WithCreate<E, D, K>,
    WithRead<D, K>,
    WithUpdate<D, K>,
    WithDelete<D, K>
    where E : DeletableDomainObject<K>,
          D : Identifiable<K>,
          K : Serializable
