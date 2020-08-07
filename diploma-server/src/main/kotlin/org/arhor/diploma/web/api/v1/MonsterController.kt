package org.arhor.diploma.web.api.v1

import org.arhor.diploma.service.Reader
import org.arhor.diploma.service.dto.Monster
import org.arhor.diploma.util.bound
import org.arhor.diploma.util.createLogger
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(
    path = ["/api/v1/monsters"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class MonsterController(private val monsterReader: Reader<Monster, String>) {

    companion object {
        @JvmStatic
        private val log = createLogger<MonsterController>()
    }

    @GetMapping
    fun getMonsters(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseEntity<List<Monster>> {

        val monsters = if ((page == null) and (size == null)) {
            log.debug("fetching all monsters")
            monsterReader.getList()
        } else {
            log.debug("fetching monsters: page {}, size {}", page, size)
            bound<Int, List<Monster>>(monsterReader::getList)(page, size)
        }

        return ResponseEntity.ok(monsters)
    }
}