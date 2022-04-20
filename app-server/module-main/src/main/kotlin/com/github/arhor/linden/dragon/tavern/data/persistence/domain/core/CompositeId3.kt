package com.github.arhor.linden.dragon.tavern.data.persistence.domain.core

import java.io.Serializable

data class CompositeId3<A, B, C>(
    var value1: A,
    var value2: B,
    var value3: C,
) : Serializable
        where A : Serializable,
              B : Serializable,
              C : Serializable




