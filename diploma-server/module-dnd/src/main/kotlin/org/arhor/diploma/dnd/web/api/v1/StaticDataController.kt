package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import org.arhor.diploma.commons.*
import org.arhor.diploma.dnd.data.repository.DataProvider
import java.io.Serializable
import java.util.function.Predicate

abstract class StaticDataController<
        T : Identifiable<K>,
        D : Identifiable<K>,
        K : Serializable>(
    private val dataProvider: DataProvider<T, D, K>,
    private val resourceName: String
) {


    abstract val log: KLogger

    protected fun getEntityDetails(name: K): D {
        log.debug { "fetching $resourceName details by name: $name" }
        return dataProvider.getDetails(name)
    }

    protected fun getEntityDetailsList(page: Int?, size: Int?): List<D> {
        return if ((page == null) && (size == null)) {
            log.debug { "fetching all $resourceName details list" }
            dataProvider.getDetailsList()
        } else {
            val safePage = page.minBound(DEFAULT_PAGE)
            val safeSize = size.maxBound(DEFAULT_SIZE)
            log.debug { "fetching $resourceName details list: page $safePage, size $safeSize" }
            dataProvider.getDetailsList(safePage, safeSize)
        }
    }

    protected fun getEntityDetailsList(page: Int?, size: Int?, query: Predicate<D>): List<D> {
        return if ((page == null) and (size == null)) {
            log.debug { "fetching all $resourceName details list" }
            dataProvider.getDetailsList(query)
        } else {
            val safePage = page.minBound(DEFAULT_PAGE)
            val safeSize = size.maxBound(DEFAULT_SIZE)
            log.debug { "fetching $resourceName details list: page $safePage, size $safeSize" }
            dataProvider.getDetailsList(safePage, safeSize)
        }
    }

    protected fun getEntity(name: K): T {
        log.debug { "fetching $resourceName by name: $name" }
        return dataProvider.getOne(name)
    }

    protected fun getEntityList(page: Int?, size: Int?): List<T> {
        return if ((page == null) and (size == null)) {
            log.debug { "fetching all $resourceName list" }
            dataProvider.getList()
        } else {
            val safePage = page.minBound(DEFAULT_PAGE)
            val safeSize = size.maxBound(DEFAULT_SIZE)
            log.debug { "fetching $resourceName list: page $safePage, size $safeSize" }
            dataProvider.getList(safePage, safeSize)
        }
    }
}