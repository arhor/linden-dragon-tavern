package org.arhor.diploma.dnd.web.api.v1

import mu.KLogging
import org.arhor.diploma.dnd.service.CharsheetService
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v1/charsheets"])
class CharacterSheetController(private val service: CharsheetService) {

    @GetMapping(produces = [MediaType.APPLICATION_PDF_VALUE])
    fun downloadCharsheet(): ResponseEntity<FileSystemResource> {
        logger.debug("downloading empty character sheet")

        val file = service.getEmptyCharsheet()
        val resource = FileSystemResource(file)

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=${file.name}")
            .contentLength(file.length())
            .body(resource)
    }

    companion object : KLogging()
}