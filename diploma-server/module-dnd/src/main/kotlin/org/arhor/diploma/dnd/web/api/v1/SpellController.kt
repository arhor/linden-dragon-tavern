package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import mu.KotlinLogging
import org.arhor.diploma.dnd.data.model.Spell
import org.arhor.diploma.dnd.data.repository.SpellProvider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1")
class SpellController(
    provider: SpellProvider,
) : StaticDataController<Spell, Spell.Details, String>(provider, "Spell") {

    override val log: KLogger
        get() = logger

    @GetMapping("/{name}/details")
    suspend fun getSpellDetails(@PathVariable name: String): ResponseEntity<Spell.Details> {
        val result = getEntityDetails(name)
        return ResponseEntity.ok().body(result)
    }

    @GetMapping("/{name}")
    suspend fun getSpell(@PathVariable name: String): ResponseEntity<Spell> {
        val result = getEntity(name)
        return ResponseEntity.ok().body(result)
    }

    @GetMapping
    suspend fun getSpellList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): ResponseEntity<List<Spell>> {
        val result = getEntityList(page, size)
        return ResponseEntity.ok().body(result)
    }
}