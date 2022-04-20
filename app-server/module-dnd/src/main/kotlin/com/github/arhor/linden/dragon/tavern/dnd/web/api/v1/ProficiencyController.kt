package com.github.arhor.linden.dragon.tavern.dnd.web.api.v1

import com.github.arhor.linden.dragon.tavern.common.Page
import com.github.arhor.linden.dragon.tavern.dnd.data.model.Proficiency
import com.github.arhor.linden.dragon.tavern.dnd.data.repository.ProficiencyRepository
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/proficiencies")
class ProficiencyController(
    repository: ProficiencyRepository,
) : StaticDataController<Proficiency, String>(repository) {

    override val resourceName: String = "Proficiency"

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
