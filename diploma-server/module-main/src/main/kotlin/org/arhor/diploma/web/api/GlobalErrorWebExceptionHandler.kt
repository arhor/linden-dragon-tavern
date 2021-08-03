package org.arhor.diploma.web.api

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import mu.KotlinLogging
import org.arhor.diploma.commons.data.DataAccessException
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.web.error.ErrorCode
import org.arhor.diploma.web.error.MessageResponse
import org.arhor.diploma.web.error.messageResponse
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.context.MessageSource
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.ResponseStatusException
import java.io.FileNotFoundException
import java.util.*

private val log = KotlinLogging.logger {}

@Component
@Order(-2)
class GlobalErrorWebExceptionHandler(
    private val messages: MessageSource,
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
        val (status, response) = convertToStatusAndResponse(
            exception = getError(request),
            lang = getLocale(request)
        )

        val mediaType = request.headers().contentType().orElse(MediaType.APPLICATION_JSON)

        return ServerResponse
            .status(status)
            .contentType(mediaType)
            .bodyValueAndAwait(response)
    }

    private fun convertToStatusAndResponse(exception: Throwable, lang: Locale): Pair<HttpStatus, MessageResponse> {
        // @formatter:off
        return when (exception) {
            is FileNotFoundException               -> handleFileNotFoundException(exception)
            is MethodArgumentTypeMismatchException -> handleMethodArgumentTypeMismatchException(exception, lang)
            is DataAccessException                 -> handleDataAccessException(exception, lang)
            is EntityNotFoundException             -> handleEntityNotFoundException(exception, lang)
            is InvalidFormatException              -> handleInvalidFormatException(exception, lang)
            is JsonProcessingException             -> handleJsonProcessingException(exception, lang)
            is MethodArgumentNotValidException     -> handleMethodArgumentNotValidException(exception, lang)
            is ResponseStatusException             -> handleResponseStatusException(exception)
            is AuthenticationException             -> handleAuthenticationException(exception, lang)
            else                                   -> handleDefault(exception)
        }
        // @formatter:on
    }

    private fun handleDefault(ex: Throwable): Pair<HttpStatus, MessageResponse> {
        log.error("Unhandled error. Please, create proper exception handler for it.", ex)
        return HttpStatus.INTERNAL_SERVER_ERROR to messageResponse {
            error {
                text = "Internal Server Error. Please, contact system administrator."
                details = listOf(ex.message)
            }
        }
    }

    private fun handleResponseStatusException(ex: ResponseStatusException): Pair<HttpStatus, MessageResponse> {
        log.error("Unhandled error. Please, create proper exception handler for it.", ex)
        return ex.status to messageResponse {
            error {
                text = "Internal Server Error. Please, contact system administrator."
            }
        }
    }

    private fun handleFileNotFoundException(ex: FileNotFoundException): Pair<HttpStatus, MessageResponse> {
        return HttpStatus.NOT_FOUND to messageResponse {
            error {
                code = ErrorCode.FILE_NOT_FOUND
                text = "File Not Found"
                details = listOf(ex.message)
            }
        }
    }

    private fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException, lang: Locale): Pair<HttpStatus, MessageResponse> {
        return HttpStatus.BAD_REQUEST to messageResponse {
            error {
                code = ErrorCode.UNCATEGORIZED
                text = lang.localize("error.wrong.argument")
                details = listOf(
                    lang.localize("error.wrong.argument.details", ex.name, ex.value)
                )
            }
        }
    }

    private fun handleDataAccessException(ex: DataAccessException, lang: Locale): Pair<HttpStatus, MessageResponse> {
        return HttpStatus.NOT_FOUND to messageResponse {
            error {
                code = ErrorCode.DATA_ACCESS_ERROR
                text = lang.localize("error.data.uncategorized")
                details = listOf(ex.message)
            }
        }
    }

    private fun handleEntityNotFoundException(ex: EntityNotFoundException, lang: Locale): Pair<HttpStatus, MessageResponse> {
        val (entityType, propName, propValue) = ex

        return HttpStatus.NOT_FOUND to messageResponse {
            error {
                code = ErrorCode.NOT_FOUND
                text = lang.localize("error.entity.notfound")
                details = listOf(
                    lang.localize("error.entity.notfound.details", entityType, propName, propValue)
                )
            }
        }
    }

    private fun handleInvalidFormatException(ex: InvalidFormatException, lang: Locale): Pair<HttpStatus, MessageResponse> {
        val parser = ex.processor as JsonParser

        return HttpStatus.BAD_REQUEST to messageResponse {
            error {
                code = ErrorCode.VALIDATION_FAILED
                text = lang.localize("error.json.format.invalid")
                details = listOf(
                    lang.localize(
                        "error.json.format.invalid.details",
                        ex.value,
                        parser.currentName,
                        ex.targetType.simpleName
                    )
                )
            }
        }
    }

    private fun handleJsonProcessingException(ex: JsonProcessingException, lang: Locale): Pair<HttpStatus, MessageResponse> {
        val value = when (val processor = ex.processor) {
            is JsonParser -> processor.currentName ?: processor.text
            else -> "[UNKNOWN JSON PROCESSOR]"
        }

        return HttpStatus.BAD_REQUEST to messageResponse {
            error {
                code = ErrorCode.UNCATEGORIZED
                text = lang.localize("error.json.parse")
                details = listOf(
                    lang.localize(
                        "error.json.parse.details",
                        value,
                        ex.location.lineNr,
                        ex.location.columnNr
                    )
                )
            }
        }
    }

    private fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException, lang: Locale): Pair<HttpStatus, MessageResponse> {
        return HttpStatus.BAD_REQUEST to messageResponse {
            error {
                code = ErrorCode.VALIDATION_FAILED
                text = lang.localize("Object validation failed")
                details = errorMessagesGroupedByField(ex, lang)
            }
        }
    }

    private fun handleAuthenticationException(ex: AuthenticationException, lang: Locale): Pair<HttpStatus, MessageResponse> {
        log.error { "Authentication failed: ${ex.message}" }
        return HttpStatus.UNAUTHORIZED to messageResponse {
            error {
                code = ErrorCode.SECURITY_VIOLATION
                text = lang.localize("error.credentials.bad")
            }
        }
    }

    private fun errorMessagesGroupedByField(
        ex: MethodArgumentNotValidException,
        lang: Locale
    ): List<FieldErrorDetails> {

        return ex.allErrors
            .map { it as FieldError }
            .groupBy({ it.field }, { it.defaultMessage })
            .map { (field, messages) ->
                FieldErrorDetails(
                    field = lang.localize("error.validation.failed", field),
                    messages = messages.filterNotNull()
                )
            }
    }

    private fun Locale.localize(label: String, vararg args: Any?): String {
        return messages.getMessage(label, args, this)
    }

    private fun getLocale(request: ServerRequest): Locale {
        return request.exchange().localeContext.locale
            ?: Locale.ENGLISH
    }

    private data class FieldErrorDetails(val field: String, val messages: List<String>)
}