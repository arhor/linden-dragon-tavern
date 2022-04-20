package com.github.arhor.linden.dragon.tavern.dnd.web.api.v1

import com.github.arhor.linden.dragon.tavern.dnd.service.CharsheetService
import mu.KLogging
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/charsheets")
class CharacterSheetController(private val service: CharsheetService) {

    @GetMapping
    fun downloadCharsheet(): ResponseEntity<Resource> {
        logger.debug("downloading empty character sheet")
        return service.getEmptyCharsheet().let {
            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=${it.filename}")
                .contentType(MediaType.APPLICATION_PDF)
                .body(it)
        }
    }

    companion object : KLogging()
}
