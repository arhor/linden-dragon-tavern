package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import mu.KotlinLogging
import org.arhor.diploma.dnd.data.model.Monster
import org.arhor.diploma.dnd.data.repository.MonsterProvider
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/v1/monsters")
class MonsterController(
    provider: MonsterProvider,
) : StaticDataController<Monster, Monster.Details, String>(provider, "Monster") {

    override val log: KLogger
        get() = logger

    @GetMapping("/{name}/details")
    suspend fun getMonsterDetails(@PathVariable name: String): Monster.Details {
        logger.debug { "$name is fetched" }
        return getEntityDetails(name)
    }

    @GetMapping("/details")
    fun getMonsterDetailsList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): List<Monster.Details> {
        return getEntityDetailsList(page, size)
    }

    @GetMapping("/{name}")
    fun getMonster(@PathVariable name: String): Monster {
        return getEntity(name)
    }

    @GetMapping
    fun getMonsterList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): List<Monster> {
        return getEntityList(page, size)
    }
}