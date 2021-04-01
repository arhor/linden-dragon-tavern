package org.arhor.diploma.dnd.data.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KLogging
import org.arhor.diploma.commons.Identifiable
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.commons.file.ChecksumCalc
import org.arhor.diploma.commons.time.MINUTE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.scheduling.annotation.Scheduled
import java.io.Serializable
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import java.util.function.Predicate
import javax.annotation.PostConstruct
import kotlin.concurrent.withLock
import kotlin.streams.toList

abstract class DataProviderImplDefault<T, D, K>(
    private val resourceLoader: ResourceLoader
) : DataProvider<T, D, K>
    where T : Identifiable<K>,
          D : Identifiable<K>,
          K : Serializable {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()
    private val lock: Lock = ReentrantLock()

    @Autowired
    private lateinit var checksumCalc: ChecksumCalc

    private lateinit var checksum: String

    protected abstract val resourceName: String
    protected abstract val resourcePath: String
    protected abstract val resourceType: Class<Array<D>>

    protected var data: Set<D> = emptySet()

    protected abstract fun shrinkData(details: D): T

    @Scheduled(fixedDelay = 5 * MINUTE, initialDelay = 5 * MINUTE)
    fun reloadOnChanges() = reload()

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

        if ((resource != null) && resource.isReadable) {
            logger.debug { "calculating checksum for '${resourceName}'" }

            lock.withLock {
                val latestChecksum = checksumCalc.calculate(resource.file)

                if (!this::checksum.isInitialized) {
                    logger.debug { "'${resourceName}'checksum calculating for the first time" }
                    checksum = latestChecksum

                    return readDataSetFromResource(resource)
                }

                if (checksum != latestChecksum) {
                    logger.debug { "'${resourceName}' checksum mismatch - old: $checksum, new: $latestChecksum" }

                    return readDataSetFromResource(resource)
                }

                logger.debug { "'${resourceName}' checksum did not changed" }

                return this.data
            }
        } else {
            logger.debug { "resource from '${resourcePath}' is not readable" }
        }

        return emptySet()
    }

    private fun readDataSetFromResource(resource: Resource): Set<D> {
        return resource
            .inputStream
            .bufferedReader()
            .use { reader -> objectMapper.createParser(reader).readValueAs(resourceType) }
            .let { values -> setOf(*values) }
    }

    companion object : KLogging()
}