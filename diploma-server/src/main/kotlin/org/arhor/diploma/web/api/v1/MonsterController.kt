package org.arhor.diploma.web.api.v1

import org.arhor.diploma.service.MonsterService
import org.arhor.diploma.service.dto.Monster
import org.arhor.diploma.service.dto.MonsterDetails
import org.arhor.diploma.util.bound
import org.arhor.diploma.util.createLogger
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(
    path = ["/api/v1/monsters"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class MonsterController(private val service: MonsterService) {

    companion object {
        @JvmStatic
        private val log = createLogger<MonsterController>()
    }

    @GetMapping(path = ["/details/{name}"])
    fun getMonsterDetails(@PathVariable name: String): ResponseEntity<MonsterDetails> {
        log.debug("fetching monster details by name: {}", name)
        val monster = service.getOne(name)
        return ResponseEntity.ok(monster)
    }

    @GetMapping(path = ["/details"])
    fun getMonsterDetailsList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseEntity<List<MonsterDetails>> {
        val monsters = if ((page == null) and (size == null)) {
            log.debug("fetching all monster details")
            service.getList()
        } else {
            log.debug("fetching monster details: page {}, size {}", page, size)
            bound<Int, List<MonsterDetails>>(service::getList)(page, size)
        }
        return ResponseEntity.ok(monsters)
    }

    @GetMapping(path = ["/{name}"])
    fun getMonster(@PathVariable name: String): ResponseEntity<Monster> {
        log.debug("fetching monster by name: {}", name)
        val monster = service.getMonsterByName(name)
        return ResponseEntity.ok(monster)
    }

    @GetMapping
    fun getMonsterList(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseEntity<List<Monster>> {
        val monsters = if ((page == null) and (size == null)) {
            log.debug("fetching all monsters")
            service.getMonsters()
        } else {
            log.debug("fetching monsters: page {}, size {}", page, size)
            bound<Int, List<Monster>>(service::getMonsters)(page, size)
        }
        return ResponseEntity.ok(monsters)
    }
}