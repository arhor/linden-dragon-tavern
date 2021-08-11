package org.arhor.diploma.dnd.web.api.v1

import mu.KLogger
import mu.KLogging
import org.arhor.diploma.commons.DEFAULT_PAGE
import org.arhor.diploma.commons.DEFAULT_SIZE
import org.arhor.diploma.commons.Page
import org.arhor.diploma.dnd.data.model.Monster
import org.arhor.diploma.dnd.data.repository.MonsterRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/monsters")
class MonsterController(
    val repository: MonsterRepository,
) : StaticDataController<Monster, String>(repository, "Monster") {

    override val log: KLogger
        get() = logger

    @GetMapping("/{name}/details")
    suspend fun getMonsterDetails(@PathVariable name: String): Monster {
        logger.debug { "$name is fetched" }
        return getEntityDetails(name)
    }

    @GetMapping("/details")
    fun getMonsterDetailsList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?,
    ): Page<Monster> {
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
        @RequestParam(required = false) sortBy: List<String>?,
        @RequestParam(required = false) sortDesc: List<Boolean>?,
        @RequestParam(required = false) search: String?,
    ): Page<Monster> {

        val query = search?.let(::monsterNameLike)

        return if (shouldFindAllEntities(page, size)) {
            if (query == null) {
                repository.getPage()
            } else {
                repository.getPage(query)
            }
        } else {
            if (query == null) {
                repository.getPage(page ?: DEFAULT_PAGE, size ?: DEFAULT_SIZE)
            } else {
                repository.getPage(page ?: DEFAULT_PAGE, size ?: DEFAULT_SIZE, query)
            }
        }
    }

    private fun monsterNameLike(search: String): (Monster) -> Boolean {
        return { it.name?.contains(search, ignoreCase = true) == true }
    }

    private fun shouldFindAllEntities(page: Int?, size: Int?): Boolean {
        return ((page == null) && (size == null)) || (size == -1)
    }

    companion object : KLogging()
}