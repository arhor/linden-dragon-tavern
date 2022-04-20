package com.github.arhor.linden.dragon.tavern.dnd.web.api.v1

import com.github.arhor.linden.dragon.tavern.common.DEFAULT_PAGE
import com.github.arhor.linden.dragon.tavern.common.DEFAULT_SIZE
import com.github.arhor.linden.dragon.tavern.common.Page
import com.github.arhor.linden.dragon.tavern.dnd.data.model.Monster
import com.github.arhor.linden.dragon.tavern.dnd.data.repository.MonsterRepository
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/monsters")
class MonsterController(
    private val repository: MonsterRepository,
    private val rsqlToSQLConverter: RSQLToSQLConverterUnsafe,
) : StaticDataController<Monster, String>(repository) {

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

//        val rsqlQueryRootNode = RSQLParser().parse(search)
//
//        val predicate = rsqlQueryRootNode.accept(rsqlToSQLConverter)

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
