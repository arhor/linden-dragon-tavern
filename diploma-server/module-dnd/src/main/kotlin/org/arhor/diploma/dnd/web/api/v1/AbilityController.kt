package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import mu.KLogging
import org.arhor.diploma.commons.Page
import org.arhor.diploma.dnd.data.model.Ability
import org.arhor.diploma.dnd.data.repository.AbilityRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/abilities")
class AbilityController(
    repository: AbilityRepository,
) : StaticDataController<Ability, String>(repository) {

    override val log: KLogger
        get() = logger

    override val resourceName: String
        get() = "Ability"

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