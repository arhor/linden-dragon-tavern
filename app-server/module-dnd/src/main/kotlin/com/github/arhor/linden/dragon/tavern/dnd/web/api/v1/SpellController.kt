package com.github.arhor.linden.dragon.tavern.dnd.web.api.v1

import com.github.arhor.linden.dragon.tavern.common.Page
import com.github.arhor.linden.dragon.tavern.dnd.data.model.Spell
import com.github.arhor.linden.dragon.tavern.dnd.data.repository.SpellRepository
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/spells")
class SpellController(repository: SpellRepository) : StaticDataController<Spell, String>(repository) {

    override val resourceName = "Spell"

    @GetMapping("/{name}")
    fun getSpell(@PathVariable name: String): Spell {
        return getEntity(name)
    }

    @GetMapping
    fun getSpellList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): Page<Spell> {
        return getEntityList(page, size)
    }

    companion object : KLogging()
}
