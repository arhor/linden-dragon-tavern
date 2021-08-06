package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import mu.KotlinLogging
import org.arhor.diploma.dnd.data.model.Ability
import org.arhor.diploma.dnd.data.repository.AbilityProvider
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/abilities")
class AbilityController(
    provider: AbilityProvider,
) : StaticDataController<Ability, Ability, String>(provider, "Ability") {

    override val log: KLogger
        get() = logger

    @GetMapping("/{name}")
    fun getAbility(@PathVariable name: String): Ability {
        return getEntity(name)
    }

    @GetMapping
    fun getAllAbilities(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): List<Ability> {
        return getEntityList(page, size)
    }
}