package com.github.arhor.linden.dragon.tavern.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.arhor.linden.dragon.tavern.common.Identifiable
import com.github.arhor.linden.dragon.tavern.common.Page
import com.github.arhor.linden.dragon.tavern.common.chunk
import com.github.arhor.linden.dragon.tavern.common.data.EntityNotFoundException
import mu.KLogging
import org.springframework.core.io.Resource
import java.io.Serializable

abstract class DataRepositoryImplDefault<T, K>(
    private val objectMapper: ObjectMapper,
    private val resourceName: String,
    private val resourceType: Class<Array<T>>,
    private var resource: Resource,
) : DataRepository<T, K>
    where T : Identifiable<K>,
          K : Serializable {

    private val data: Set<T> by lazy(::loadDataFromResource)
    private val indexById: Map<K?, T> by lazy { data.associateBy { it.id } }

    override fun count(): Int {
        return data.size
    }

    override fun count(query: (T) -> Boolean): Int {
        return data.count(query)
    }

    override fun findById(id: K): T {
        return indexById[id] ?: throw EntityNotFoundException(
            entityType = resourceName,
            propName = "id",
            propValue = id
        )
    }

    override fun getPage(): Page<T> {
        return Page(data)
    }

    override fun getPage(page: Int, size: Int): Page<T> {
        return Page(data.chunk(page, size), data.size)
    }

    override fun getPage(query: (T) -> Boolean): Page<T> {
        return Page(data.filter(query))
    }

    override fun getPage(page: Int, size: Int, query: (T) -> Boolean): Page<T> {
        return data.filter(query).let { Page(it.chunk(page, size), it.size) }
    }

    private fun loadDataFromResource(): Set<T> {
        logger.info { "loading data from '${resource.filename}'" }

        return runCatching {
            resource.inputStream.bufferedReader().use { reader ->
                objectMapper
                    .createParser(reader)
                    .readValueAs(resourceType)
            }
        }.fold(
            onSuccess = { value: Array<T> ->
                setOf(*value)
            },
            onFailure = { exception: Throwable ->
                logger.error(exception) { "an exception occurred reading resource from '${resource.filename}'" }
                emptySet()
            }
        )
    }

    companion object : KLogging()
}
