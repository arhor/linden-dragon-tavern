package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import mu.KotlinLogging
import org.arhor.diploma.dnd.data.model.Proficiency
import org.arhor.diploma.dnd.data.repository.ProficiencyProvider
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/proficiencies")
class ProficiencyController(
    provider: ProficiencyProvider,
) : StaticDataController<Proficiency, Proficiency, String>(provider, "Proficiency") {

    override val log: KLogger
        get() = logger

    @GetMapping("/{name}")
    fun getProficiency(@PathVariable name: String): Proficiency {
        return getEntity(name)
    }

    @GetMapping
    fun getAllProficiencies(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): List<Proficiency> {
        return getEntityList(page, size)
    }
}