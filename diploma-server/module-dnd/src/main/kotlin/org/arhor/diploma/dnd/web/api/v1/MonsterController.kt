package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import mu.KLogging
import org.arhor.diploma.dnd.data.model.Monster
import org.arhor.diploma.dnd.data.repository.MonsterProvider
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    path = ["/api/v1/monsters"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class MonsterController(
    provider: MonsterProvider
) : StaticDataController<Monster, Monster.Details, String>(provider, "Monster") {

    override val log: KLogger
        get() = logger

    @GetMapping("/{name}/details")
    fun getMonsterDetails(@PathVariable name: String): ResponseEntity<Monster.Details> {
        return getEntityDetails(name)
    }

    @GetMapping("/details")
    fun getMonsterDetailsList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseEntity<List<Monster.Details>> {
        return getEntityDetailsList(page, size)
    }

    @GetMapping("/{name}")
    fun getMonster(@PathVariable name: String): ResponseEntity<Monster> {
        return getEntity(name)
    }

    @GetMapping
    fun getMonsterList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseEntity<List<Monster>> {
        return getEntityList(page, size)
    }

    companion object : KLogging()
}