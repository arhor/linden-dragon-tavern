package com.github.arhor.linden.dragon.tavern.service

import com.github.arhor.linden.dragon.tavern.common.Identifiable
import java.io.Serializable

interface WithUpdate<T : Identifiable<K>, K : Serializable> {

    fun update(item: T): T
}
