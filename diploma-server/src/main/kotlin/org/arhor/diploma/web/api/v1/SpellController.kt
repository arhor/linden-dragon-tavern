package org.arhor.diploma.web.api.v1

import org.arhor.diploma.data.file.SpellProvider
import org.arhor.diploma.data.file.model.Spell
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.invoke.MethodHandles

@RestController
@RequestMapping(
    path = ["/api/v1/spells"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class SpellController(
    provider: SpellProvider
): StaticDataController<Spell, Spell.Details, String>(provider, log, "Spell") {

    @PostMapping(path = ["/reload"])
    fun reloadDataProvider() {
        dataProvider.reload()
    }

    @GetMapping(path = ["/{name}/details"])
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

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}