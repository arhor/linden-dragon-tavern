package org.arhor.diploma.dnd.web.api.v1

import kotlinx.coroutines.reactive.awaitSingle
import mu.KLogger
import mu.KotlinLogging
import org.arhor.diploma.dnd.data.model.Monster
import org.arhor.diploma.dnd.data.repository.MonsterProvider
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.queryParamOrNull
import reactor.core.publisher.Mono
import java.io.FileNotFoundException

private val logger = KotlinLogging.logger {}

@Component
class MonsterController(
    provider: MonsterProvider
) : StaticDataController<Monster, Monster.Details, String>(provider, "Monster") {

    override val log: KLogger
        get() = logger

    suspend fun getMonsterDetails(request: ServerRequest): ServerResponse {
        val name = request.pathVariable("name")
        val result = getEntityDetails(name)

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(result)
    }

    suspend fun getMonsterDetailsList(request: ServerRequest): ServerResponse {
        val page = request.queryParamOrNull("page")?.toInt()
        val size = request.queryParamOrNull("size")?.toInt()

        val result = getEntityDetailsList(page, size)

        return ServerResponse.ok().bodyValueAndAwait(result)
    }

    suspend fun getMonster(request: ServerRequest): ServerResponse {
        val name = request.pathVariable("name")

        val result = getEntity(name)

        return ServerResponse.ok().bodyValueAndAwait(result)
    }

    suspend fun getMonsterList(request: ServerRequest): ServerResponse {
        val page = request.queryParamOrNull("page")?.toInt()
        val size = request.queryParamOrNull("size")?.toInt()

        val result = getEntityList(page, size)

        return ServerResponse.ok().bodyValueAndAwait(result)
    }
}