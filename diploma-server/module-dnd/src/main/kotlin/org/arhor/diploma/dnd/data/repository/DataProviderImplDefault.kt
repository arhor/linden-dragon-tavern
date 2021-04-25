package org.arhor.diploma.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import java.io.Serializable
import java.util.function.Predicate
import javax.annotation.PostConstruct
import kotlin.streams.toList

private val logger = KotlinLogging.logger {}

abstract class DataProviderImplDefault<T, D, K>(
    private val resourceLoader: ResourceLoader
) : DataProvider<T, D, K>
    where T : Identifiable<K>,
          D : Identifiable<K>,
          K : Serializable {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    protected abstract val resourceName: String
    protected abstract val resourcePath: String
    protected abstract val resourceType: Class<Array<D>>

    protected var data: Set<D> = emptySet()

    protected abstract fun shrinkData(details: D): T

    override fun getOne(id: K): T {
        return getDetails(id).let(::shrinkData)
    }

    override fun getList(): List<T> {
        return getDetailsList().map(::shrinkData)
    }

    override fun getList(page: Int, size: Int): List<T> {
        return getDetailsList(page, size).map(::shrinkData)
    }

    override fun getDetails(id: K): D {
        return data.find { it.id == id } ?: throw EntityNotFoundException(
            entityType = resourceName,
            propName = "id",
            propValue = id
        )
    }

    override fun getDetailsList(): List<D> {
        return data.toList()
    }

    override fun getDetailsList(query: Predicate<D>): List<D> {
        return data.filter { query.test(it) }.toList()
    }

    override fun getDetailsList(page: Int, size: Int): List<D> {
        val offset = ((page - 1) * size).toLong()
        val amount = size.toLong()

        return data.takeIf { it.isNotEmpty() }
            ?.stream()
            ?.skip(offset)
            ?.limit(amount)
            ?.toList()
            ?: emptyList()
    }

    override fun getDetailsList(page: Int, size: Int, query: Predicate<D>): List<D> {
        val offset = ((page - 1) * size).toLong()
        val amount = size.toLong()

        return data.takeIf { it.isNotEmpty() }
            ?.stream()
            ?.filter(query)
            ?.skip(offset)
            ?.limit(amount)
            ?.toList()
            ?: emptyList()
    }

    @PostConstruct
    override fun reload() {
        try {
            data = loadData()
        } catch (e: Throwable) {
            logger.error("Attempt to reload data-file was failed", e)
        }
    }

    private fun loadData(): Set<D> {
        logger.debug { "loading data from '${resourcePath}'" }

        val resource = try {
            resourceLoader.getResource(resourcePath)
        } catch (ex: Exception) {
            logger.error(ex) { "an error occurred trying to load resource from '${resourcePath}'" }
            null
        }

        return if ((resource != null) && resource.isReadable) {
            readDataSetFromResource(resource)
        } else {
            logger.debug { "resource from '${resourcePath}' is not readable" }
            emptySet()
        }
    }

    private fun readDataSetFromResource(resource: Resource): Set<D> {
        return resource
            .inputStream
            .bufferedReader()
            .use { reader -> objectMapper.createParser(reader).readValueAs(resourceType) }
            .let { values -> setOf(*values) }
    }
}
