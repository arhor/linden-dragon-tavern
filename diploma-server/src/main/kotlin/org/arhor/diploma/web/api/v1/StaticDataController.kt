package org.arhor.diploma.web.api.v1

import org.arhor.diploma.core.Identifiable
import org.arhor.diploma.service.data.DataProvider
import org.arhor.diploma.util.bound
import org.slf4j.Logger
import org.springframework.http.ResponseEntity
import java.io.Serializable

abstract class StaticDataController<
        T : Identifiable<K>,
        D : Identifiable<K>,
        K : Serializable>(
    protected val dataProvider: DataProvider<T, D, K>,
    private val log: Logger,
    private val resourceName: String
) {

    fun getEntityDetails(name: K): ResponseEntity<D> {
        log.debug("fetching {} details by name: {}", resourceName, name)
        val entityDetails = dataProvider.getDetails(name)
        return ResponseEntity.ok(entityDetails)
    }

    fun getEntityDetailsList(page: Int?, size: Int?): ResponseEntity<List<D>> {
        val entityDetailsList = if ((page == null) and (size == null)) {
            log.debug("fetching all {} details list", resourceName)
            dataProvider.getDetailsList()
        } else {
            log.debug("fetching {} details list: page {}, size {}", resourceName, page, size)
            bound<Int, List<D>>(dataProvider::getDetailsList)(page, size)
        }
        return ResponseEntity.ok(entityDetailsList)
    }

    fun getEntity(name: K): ResponseEntity<T> {
        log.debug("fetching {} by name: {}", resourceName, name)
        val entity = dataProvider.getOne(name)
        return ResponseEntity.ok(entity)
    }

    fun getEntityList(page: Int?, size: Int?): ResponseEntity<List<T>> {
        val entityList = if ((page == null) and (size == null)) {
            log.debug("fetching all {} list", resourceName)
            dataProvider.getList()
        } else {
            log.debug("fetching {} list: page {}, size {}", resourceName, page, size)
            bound<Int, List<T>>(dataProvider::getList)(page, size)
        }
        return ResponseEntity.ok(entityList)
    }
}