package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import mu.KotlinLogging
import org.arhor.diploma.dnd.data.model.Spell
import org.arhor.diploma.dnd.data.repository.SpellProvider
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.queryParamOrNull

private val logger = KotlinLogging.logger {}

@Component
class SpellController(
    provider: SpellProvider,
) : StaticDataController<Spell, Spell.Details, String>(provider, "Spell") {

    override val log: KLogger
        get() = logger

    suspend fun getSpellDetails(request: ServerRequest): ServerResponse {
        val name = request.pathVariable("name")
        val result = getEntityDetails(name)

        return ServerResponse.ok().bodyValueAndAwait(result)
    }

    suspend fun getSpell(request: ServerRequest): ServerResponse {
        val name = request.pathVariable("name")

        val result = getEntity(name)

        return ServerResponse.ok().bodyValueAndAwait(result)
    }

    suspend fun getSpellList(request: ServerRequest): ServerResponse {
        val page = request.queryParamOrNull("page")?.toInt()
        val size = request.queryParamOrNull("size")?.toInt()

        val result = getEntityList(page, size)

        return ServerResponse.ok().bodyValueAndAwait(result)
    }
}