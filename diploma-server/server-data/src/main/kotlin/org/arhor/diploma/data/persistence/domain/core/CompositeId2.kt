package org.arhor.diploma.data.persistence.domain.core

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
data class CompositeId2<A, B>(
    var value1: A,
    var value2: B,
) : Serializable




