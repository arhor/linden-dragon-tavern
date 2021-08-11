package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import mu.KotlinLogging
import org.arhor.diploma.commons.Page
import org.arhor.diploma.dnd.data.model.Spell
import org.arhor.diploma.dnd.data.repository.SpellRepository
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/spells")
class SpellController(
    provider: SpellRepository,
) : StaticDataController<Spell, String>(provider, "Spell") {

    override val log: KLogger
        get() = logger

    @GetMapping("/{name}/details")
    suspend fun getSpellDetails(@PathVariable name: String): Spell {
        return getEntityDetails(name)
    }

    @GetMapping("/{name}")
    suspend fun getSpell(@PathVariable name: String): Spell {
        return getEntity(name)
    }

    @GetMapping
    suspend fun getSpellList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): Page<Spell> {
        return getEntityList(page, size)
    }
}