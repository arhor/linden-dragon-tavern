package com.github.arhor.linden.dragon.tavern.dnd.web.api.v1

import com.github.arhor.linden.dragon.tavern.common.DEFAULT_PAGE
import com.github.arhor.linden.dragon.tavern.common.DEFAULT_SIZE
import com.github.arhor.linden.dragon.tavern.common.Identifiable
import com.github.arhor.linden.dragon.tavern.common.Page
import com.github.arhor.linden.dragon.tavern.common.maxBound
import com.github.arhor.linden.dragon.tavern.common.minBound
import com.github.arhor.linden.dragon.tavern.dnd.data.repository.DataRepository
import mu.KLoggable
import java.io.Serializable

context(KLoggable)
abstract class StaticDataController<T, K>(
    private val dataRepository: DataRepository<T, K>,
) where T : Identifiable<K>,
        K : Serializable {

    protected abstract val resourceName: String

    protected fun getEntity(name: K): T {
        logger.debug { "fetching $resourceName by name: $name" }
        return dataRepository.findById(name)
    }

    protected fun getEntityList(page: Int?, size: Int?): Page<T> {
        return if (((page == null) && (size == null)) || size == -1) {
            logger.debug { "fetching all $resourceName list" }
            dataRepository.getPage()
        } else {
            val safePage = page.minBound(DEFAULT_PAGE)
            val safeSize = size.maxBound(DEFAULT_SIZE)
            logger.debug { "fetching $resourceName list: page $safePage, size $safeSize" }
            dataRepository.getPage(safePage, safeSize)
        }
    }
}
