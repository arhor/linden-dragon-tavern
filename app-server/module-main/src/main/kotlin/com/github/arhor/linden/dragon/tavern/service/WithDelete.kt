package com.github.arhor.linden.dragon.tavern.service

import com.github.arhor.linden.dragon.tavern.common.Identifiable
import java.io.Serializable

interface WithDelete<T : Identifiable<K>, K : Serializable> {

    fun delete(id: K)

    fun delete(item: T)
}
