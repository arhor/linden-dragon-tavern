package org.arhor.diploma.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.service.Reader
import org.springframework.core.io.ResourceLoader
import java.io.Serializable
import kotlin.streams.toList

abstract class DataReaderImpl<T : Identifiable<K>, K : Serializable>(
    private val resourceLoader: ResourceLoader
) : Reader<T, K> {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    protected open val schemaPath: String? = null
    protected abstract val resourcePath: String
    protected abstract val resourceType: Class<Array<T>>

    protected val data: Set<T> by lazy(::loadData)

    override fun getOne(id: K): T {
        return data.first { it.id == id }
    }

    override fun getList(): List<T> {
        return data.toList()
    }

    override fun getList(page: Int, size: Int): List<T> {
        val offset = ((page - 1) * size).toLong()
        val amount = size.toLong()

        return data.stream().skip(offset).limit(amount).toList()
    }

    private fun loadData(): Set<T> {
        return resourceLoader.getResource(resourcePath).takeIf { it.isReadable }
            ?.inputStream
            ?.bufferedReader()
            ?.use { reader -> objectMapper.createParser(reader).readValueAs(resourceType) }
            ?.let { values -> setOf(*values) }
            ?: emptySet()
    }

    override fun getTotalSize(): Long {
        return data.size.toLong()
    }
}