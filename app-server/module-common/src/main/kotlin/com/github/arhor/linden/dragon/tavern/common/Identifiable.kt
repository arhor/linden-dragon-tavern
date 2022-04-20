package com.github.arhor.linden.dragon.tavern.common

import java.io.Serializable

interface Identifiable<T : Serializable> {

    val id: T?
}
