package org.arhor.diploma.dnd.web.api.v1

import mu.KLogging
import org.arhor.diploma.commons.Page
import org.arhor.diploma.dnd.data.model.Spell
import org.arhor.diploma.dnd.data.repository.SpellRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/spells")
class SpellController(repository: SpellRepository) : StaticDataController<Spell, String>(repository) {

    override val log = logger
    override val resourceName = "Spell"

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

    companion object : KLogging()
}