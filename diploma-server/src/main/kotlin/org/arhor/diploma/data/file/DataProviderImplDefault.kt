package org.arhor.diploma.data.file

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.arhor.diploma.core.Identifiable
import org.springframework.core.io.ResourceLoader
import java.io.Serializable
import kotlin.streams.toList

abstract class DataProviderImplDefault<
        T : Identifiable<K>,
        D : Identifiable<K>,
        K : Serializable
        >(
    private val resourceLoader: ResourceLoader
) : DataProvider<T, D, K> {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    protected open val schemaPath: String? = null
    protected abstract val resourcePath: String
    protected abstract val resourceType: Class<Array<D>>

    protected var data: Set<D> = loadData()

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
        return data.first { it.getId() == id }
    }

    override fun getDetailsList(): List<D> {
        return data.toList()
    }

    override fun getDetailsList(page: Int, size: Int): List<D> {
        val offset = ((page - 1) * size).toLong()
        val amount = size.toLong()

        return data.stream().skip(offset).limit(amount).toList()
    }

    override fun reload() {
        data = loadData()
    }

    private fun loadData(): Set<D> {
        return resourceLoader.getResource(resourcePath).takeIf { it.isReadable }
            ?.inputStream
            ?.bufferedReader()
            ?.use { reader -> objectMapper.createParser(reader).readValueAs(resourceType) }
            ?.let { values -> setOf(*values) }
            ?: emptySet()
    }
}