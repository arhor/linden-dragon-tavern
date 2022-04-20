package com.github.arhor.linden.dragon.tavern.service

import com.github.arhor.linden.dragon.tavern.common.Identifiable
import com.github.arhor.linden.dragon.tavern.data.persistence.domain.core.DeletableDomainObject
import java.io.Serializable

interface WithCreate<E, D, K>
    where E : DeletableDomainObject<K>,
          D : Identifiable<K>,
          K : Serializable {

    fun create(item: D): D
}
