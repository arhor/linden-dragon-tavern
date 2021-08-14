package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import mu.KLogging
import org.arhor.diploma.commons.Page
import org.arhor.diploma.dnd.data.model.Proficiency
import org.arhor.diploma.dnd.data.repository.ProficiencyRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/proficiencies")
class ProficiencyController(
    repository: ProficiencyRepository,
) : StaticDataController<Proficiency, String>(repository, "Proficiency") {

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
    ): Page<Proficiency> {
        return getEntityList(page, size)
    }

    companion object : KLogging()
}