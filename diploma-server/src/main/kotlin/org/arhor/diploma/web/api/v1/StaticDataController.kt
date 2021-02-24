package org.arhor.diploma.web.api.v1

import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.data.file.DataProvider
import org.arhor.diploma.util.DEFAULT_PAGE
import org.arhor.diploma.util.DEFAULT_SIZE
import org.arhor.diploma.util.maxBound
import org.arhor.diploma.util.minBound
import org.slf4j.Logger
import org.springframework.http.ResponseEntity
import java.io.Serializable
import java.util.function.Predicate

abstract class StaticDataController<
        T : Identifiable<K>,
        D : Identifiable<K>,
        K : Serializable>(
    protected val dataProvider: DataProvider<T, D, K>,
    private val log: Logger,
    private val resourceName: String
) {

    protected fun getEntityDetails(name: K): ResponseEntity<D> {
        log.debug("fetching {} details by name: {}", resourceName, name)
        val entityDetails = dataProvider.getDetails(name)
        return ResponseEntity.ok(entityDetails)
    }

    protected fun getEntityDetailsList(page: Int?, size: Int?): ResponseEntity<List<D>> {
        val entityDetailsList = if ((page == null) && (size == null)) {
            log.debug("fetching all {} details list", resourceName)
            dataProvider.getDetailsList()
        } else {
            val safePage = page.minBound(DEFAULT_PAGE)
            val safeSize = size.maxBound(DEFAULT_SIZE)
            log.debug("fetching {} details list: page {}, size {}", resourceName, safePage, safeSize)
            dataProvider.getDetailsList(safePage, safeSize)
        }
        return ResponseEntity.ok(entityDetailsList)
    }

    protected fun getEntityDetailsList(page: Int?, size: Int?, query: Predicate<D>): ResponseEntity<List<D>> {
        val entityDetailsList = if ((page == null) and (size == null)) {
            log.debug("fetching all {} details list", resourceName)
            dataProvider.getDetailsList(query)
        } else {
            val safePage = page.minBound(DEFAULT_PAGE)
            val safeSize = size.maxBound(DEFAULT_SIZE)
            log.debug("fetching {} details list: page {}, size {}", resourceName, safePage, safeSize)
            dataProvider.getDetailsList(safePage, safeSize)
        }
        return ResponseEntity.ok(entityDetailsList)
    }

    protected fun getEntity(name: K): ResponseEntity<T> {
        log.debug("fetching {} by name: {}", resourceName, name)
        val entity = dataProvider.getOne(name)
        return ResponseEntity.ok(entity)
    }

    protected fun getEntityList(page: Int?, size: Int?): ResponseEntity<List<T>> {
        val entityList = if ((page == null) and (size == null)) {
            log.debug("fetching all {} list", resourceName)
            dataProvider.getList()
        } else {
            val safePage = page.minBound(DEFAULT_PAGE)
            val safeSize = size.maxBound(DEFAULT_SIZE)
            log.debug("fetching {} list: page {}, size {}", resourceName, safePage, safeSize)
            dataProvider.getList(safePage, safeSize)
        }
        return ResponseEntity.ok(entityList)
    }
}