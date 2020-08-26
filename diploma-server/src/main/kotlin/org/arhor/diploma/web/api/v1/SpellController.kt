package org.arhor.diploma.web.api.v1

import org.arhor.diploma.data.file.model.Spell
import org.arhor.diploma.data.file.SpellProvider
import org.arhor.diploma.util.createLogger
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    path = ["/api/v1/spells"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class SpellController(
    provider: SpellProvider
): StaticDataController<Spell, Spell.Details, String>(provider, log, "Spell") {

    companion object {
        @JvmStatic
        private val log = createLogger<SpellController>()
    }

    @GetMapping(path = ["/details/{name}"])
    fun getSpellDetails(@PathVariable name: String): ResponseEntity<Spell.Details> {
        return getEntityDetails(name)
    }

    @GetMapping(path = ["/details"])
    fun getSpellDetailsList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseEntity<List<Spell.Details>> {
        return getEntityDetailsList(page, size)
    }

    @GetMapping(path = ["/{name}"])
    fun getSpell(@PathVariable name: String): ResponseEntity<Spell> {
        return getEntity(name)
    }

    @GetMapping
    fun getSpellList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseEntity<List<Spell>> {
        return getEntityList(page, size)
    }
}