package org.arhor.diploma.dnd.web.api.v1

import cz.jirutka.rsql.parser.RSQLParser
import cz.jirutka.rsql.parser.ast.*
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
    private val repository: MonsterRepository,
    private val rsqlToSQLConverter: RSQLToSQLConverter,
) : StaticDataController<Monster, String>(repository) {

    override val log = logger
    override val resourceName = "Monster"

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

        val rsqlQueryRootNode = RSQLParser().parse(search)

        val predicate = rsqlQueryRootNode.accept(rsqlToSQLConverter)

        val query = search?.let(Monster::nameLike)

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

    private fun shouldFindAllEntities(page: Int?, size: Int?): Boolean {
        return ((page == null) && (size == null)) || (size == -1)
    }

    companion object : KLogging()
}
