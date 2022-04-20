package com.github.arhor.linden.dragon.tavern.service

import com.github.arhor.linden.dragon.tavern.common.Identifiable
import java.io.Serializable

interface WithRead<T : Identifiable<K>, K : Serializable> {

    fun getOne(id: K): T

    fun getList(): List<T>

    fun getList(page: Int, size: Int): List<T>

    fun getTotalSize(): Long
}
