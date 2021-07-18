package org.arhor.diploma.dnd.web.api.v1

import mu.KotlinLogging
import org.arhor.diploma.dnd.service.CharsheetService
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api/charsheets")
class CharacterSheetController(private val service: CharsheetService) {

    @GetMapping
    suspend fun downloadCharsheet(): ResponseEntity<Resource> {
        logger.debug("downloading empty character sheet")

        val resource = service.getEmptyCharsheet()

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=${resource.filename}")
            .contentType(MediaType.APPLICATION_PDF)
            .body(resource)
    }
}