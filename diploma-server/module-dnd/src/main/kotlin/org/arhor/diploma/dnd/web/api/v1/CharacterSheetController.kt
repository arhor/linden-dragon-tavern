package org.arhor.diploma.dnd.web.api.v1

import mu.KotlinLogging
import org.arhor.diploma.dnd.service.CharsheetService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

private val logger = KotlinLogging.logger {}

@Component
class CharacterSheetController(private val service: CharsheetService) {

    suspend fun downloadCharsheet(request: ServerRequest): ServerResponse {
        logger.debug("downloading empty character sheet")

        val resource = service.getEmptyCharsheet()

        return ServerResponse.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=${resource.filename}")
            .contentType(MediaType.APPLICATION_PDF)
            .bodyValueAndAwait(resource)
    }
}