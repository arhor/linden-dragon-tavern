package org.arhor.diploma.web.api

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import java.util.*

@Component
@Order(-2)
class GlobalErrorWebExceptionHandler(
    private val exceptionConverter: ExceptionConverter,
    attributes: ErrorAttributes,
    appContext: ApplicationContext,
    configurer: ServerCodecConfigurer,
    properties: WebProperties,
) : AbstractErrorWebExceptionHandler(attributes, properties.resources, appContext) {

    init {
        super.setMessageWriters(configurer.writers)
        super.setMessageReaders(configurer.readers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes) = coRouter {
        RequestPredicates.all().invoke(::renderErrorResponse)
    }

    private suspend fun renderErrorResponse(request: ServerRequest): ServerResponse {
        val (status, response) =
            exceptionConverter.convertToStatusAndResponse(
                getError(request),
                getLocale(request)
            )
        return ServerResponse
            .status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(response)
    }

    private fun getLocale(request: ServerRequest): Locale {
        return request.exchange().localeContext.locale
            ?: Locale.ENGLISH
    }
}