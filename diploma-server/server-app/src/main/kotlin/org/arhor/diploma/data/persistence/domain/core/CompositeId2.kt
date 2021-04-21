package org.arhor.diploma.data.persistence.domain.core

import java.io.Serializable

data class CompositeId2<A, B>(
    var value1: A,
    var value2: B,
) : Serializable
        where A : Serializable,
              B : Serializable




