package org.arhor.diploma.data.persistence.domain.core

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class CompositeId3<A, B, C>(
    var value1: A,
    var value2: B,
    var value3: C,
) : Serializable
        where A : Serializable,
              B : Serializable,
              C : Serializable




