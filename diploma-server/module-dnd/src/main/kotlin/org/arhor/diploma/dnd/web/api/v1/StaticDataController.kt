package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import org.arhor.diploma.commons.*
import org.arhor.diploma.dnd.data.repository.DataRepository
import java.io.Serializable

abstract class StaticDataController<T, K>(
    private val dataRepository: DataRepository<T, K>,
    private val resourceName: String,
) where T : Identifiable<K>,
        K : Serializable {

    abstract val log: KLogger

    protected fun getEntityDetails(name: K): T {
        log.debug { "fetching $resourceName details by name: $name" }
        return dataRepository.findById(name)
    }

    protected fun getEntityDetailsList(page: Int?, size: Int?): Page<T> {
        return if ((page == null) && (size == null)) {
            log.debug { "fetching all $resourceName details list" }
            dataRepository.getPage()
        } else {
            val safePage = page.minBound(DEFAULT_PAGE)
            val safeSize = size.maxBound(DEFAULT_SIZE)
            log.debug { "fetching $resourceName details list: page $safePage, size $safeSize" }
            dataRepository.getPage(safePage, safeSize)
        }
    }

    protected fun getEntityDetailsList(page: Int?, size: Int?, query: (T) -> Boolean): Page<T> {
        return if ((page == null) and (size == null)) {
            log.debug { "fetching all $resourceName details list" }
            dataRepository.getPage(query)
        } else {
            val safePage = page.minBound(DEFAULT_PAGE)
            val safeSize = size.maxBound(DEFAULT_SIZE)
            log.debug { "fetching $resourceName details list: page $safePage, size $safeSize" }
            dataRepository.getPage(safePage, safeSize)
        }
    }

    protected fun getEntity(name: K): T {
        log.debug { "fetching $resourceName by name: $name" }
        return dataRepository.findById(name)
    }

    protected fun getEntityList(page: Int?, size: Int?): Page<T> {
        return if (((page == null) && (size == null)) || size == -1) {
            log.debug { "fetching all $resourceName list" }
            dataRepository.getPage()
        } else {
            val safePage = page.minBound(DEFAULT_PAGE)
            val safeSize = size.maxBound(DEFAULT_SIZE)
            log.debug { "fetching $resourceName list: page $safePage, size $safeSize" }
            dataRepository.getPage(safePage, safeSize)
        }
    }
}