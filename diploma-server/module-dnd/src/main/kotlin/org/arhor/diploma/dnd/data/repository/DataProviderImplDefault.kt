package org.arhor.diploma.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.springframework.core.io.Resource
import java.io.Serializable
import java.util.function.Predicate

private val logger = KotlinLogging.logger {}

abstract class DataProviderImplDefault<T, D, K>(
    private val objectMapper: ObjectMapper,
) : DataProvider<T, D, K>
    where T : Identifiable<K>,
          D : Identifiable<K>,
          K : Serializable {

    private val data: Set<D> by lazy(::loadDataFromResource)
    private val indexById: Map<K?, D> by lazy { data.associateBy { it.id } }

    protected abstract val resourceName: String
    protected abstract val resourceType: Class<Array<D>>
    protected abstract var resource: Resource

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
        return indexById[id] ?: throw EntityNotFoundException(
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
        return when {
            data.isEmpty() -> emptyList()
            else -> data.asSequence()
                .drop((page - 1) * size)
                .take(size)
                .toList()
        }
    }

    override fun getDetailsList(page: Int, size: Int, query: Predicate<D>): List<D> {
        return when {
            data.isEmpty() -> emptyList()
            else -> getDetailsList(page, size).filter { query.test(it) }
        }
    }

    private fun loadDataFromResource(): Set<D> {
        logger.info { "loading data from '${resource.filename}'" }

        return runCatching {
            resource.inputStream.bufferedReader().use { reader ->
                objectMapper
                    .createParser(reader)
                    .readValueAs(resourceType)
            }
        }.fold(
            onSuccess = { value: Array<D> ->
                setOf(*value)
            },
            onFailure = { exception: Throwable ->
                logger.error(exception) { "an exception occurred reading resource from '${resource.filename}'" }
                emptySet()
            }
        )
    }
}
