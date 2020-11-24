package org.arhor.diploma.web.api.v1

import org.arhor.diploma.data.file.MonsterProvider
import org.arhor.diploma.data.file.model.Monster
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.invoke.MethodHandles

@RestController
@RequestMapping(
    path = ["/api/v1/monsters"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class MonsterController(
    provider: MonsterProvider
) : StaticDataController<Monster, Monster.Details, String>(provider, log, "Monster") {

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

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
    }
}