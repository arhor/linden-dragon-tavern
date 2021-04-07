package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import mu.KotlinLogging
import org.arhor.diploma.dnd.data.model.Spell
import org.arhor.diploma.dnd.data.repository.SpellProvider
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping(
    path = ["/api/v1/spells"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class SpellController(
    provider: SpellProvider,
): StaticDataController<Spell, Spell.Details, String>(provider, "Spell") {

    override val log: KLogger
        get() = logger

    @PostMapping(path = ["/reload"])
    fun reloadDataProvider() {
        dataProvider.reload()
    }

    @GetMapping(path = ["/{name}/details"])
    fun getSpellDetails(@PathVariable name: String): ResponseEntity<Spell.Details> {
        return getEntityDetails(name)
    }

    @GetMapping(path = ["/{name}"])
    fun getSpell(@PathVariable name: String): ResponseEntity<Spell> {
        return getEntity(name)
    }

    @GetMapping
    fun getSpellList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): ResponseEntity<List<Spell>> {
        return getEntityList(page, size)
    }
}