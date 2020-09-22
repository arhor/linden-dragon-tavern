package org.arhor.diploma.web.api.v1

import org.arhor.diploma.data.file.model.Spell
import org.arhor.diploma.data.file.SpellProvider
import org.arhor.diploma.util.findAll
import org.arhor.diploma.util.createLogger
import org.arhor.diploma.util.equalTo
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.function.Predicate

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

    @PostMapping(path = ["/reload"])
    fun reloadDataProvider() {
        dataProvider.reload()
    }

    @GetMapping(path = ["/details/{name}"])
    fun getSpellDetails(@PathVariable name: String): ResponseEntity<Spell.Details> {
        return getEntityDetails(name)
    }

    @GetMapping(path = ["/details"])
    fun getSpellDetailsList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) level: Byte?,
        @RequestParam(required = false) ritual: Boolean?,
        @RequestParam(required = false) components: Set<Spell.Component>?,
        @RequestParam(required = false) concentration: Boolean?,
        @RequestParam(required = false) school: String?,
    ): ResponseEntity<List<Spell.Details>> {
        return getEntityDetailsList(
            page,
            size,
            Spell.Finder(name, level, ritual, components, concentration, school)
        )
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