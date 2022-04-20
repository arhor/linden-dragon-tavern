package com.github.arhor.linden.dragon.tavern.dnd.web.api.v1

import com.github.arhor.linden.dragon.tavern.common.Page
import com.github.arhor.linden.dragon.tavern.dnd.data.model.Ability
import com.github.arhor.linden.dragon.tavern.dnd.data.repository.AbilityRepository
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/abilities")
class AbilityController(
    repository: AbilityRepository,
) : StaticDataController<Ability, String>(repository) {

    override val resourceName: String = "Ability"

    @GetMapping("/{name}")
    fun getAbility(@PathVariable name: String): Ability {
        return getEntity(name)
    }

    @GetMapping
    fun getAllAbilities(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): Page<Ability> {
        return getEntityList(page, size)
    }

    companion object : KLogging()
}
