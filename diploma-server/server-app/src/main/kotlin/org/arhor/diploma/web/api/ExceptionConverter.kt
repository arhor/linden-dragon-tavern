package org.arhor.diploma.web.api

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import mu.KotlinLogging
import org.arhor.diploma.commons.data.DataAccessException
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.web.model.ErrorCode
import org.arhor.diploma.web.model.MessageResponse
import org.arhor.diploma.web.model.messageResponse
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.server.ResponseStatusException
import java.io.FileNotFoundException
import java.util.*

private val log = KotlinLogging.logger {}

@Component
class ExceptionConverter(private val messages: MessageSource) {

    fun convertToStatusAndResponse(exception: Throwable, lang: Locale): Pair<HttpStatus, MessageResponse> {
        // @formatter:off
        return when (exception) {
            is FileNotFoundException               -> handle(exception)
            is MethodArgumentTypeMismatchException -> handle(exception, lang)
            is DataAccessException                 -> handle(exception, lang)
            is EntityNotFoundException             -> handle(exception, lang)
            is InvalidFormatException              -> handle(exception, lang)
            is JsonProcessingException             -> handle(exception, lang)
            is MethodArgumentNotValidException     -> handle(exception, lang)
            is ResponseStatusException             -> handle(exception)
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

    private fun handle(ex: ResponseStatusException): Pair<HttpStatus, MessageResponse> {
        log.error("Unhandled error. Please, create proper exception handler for it.", ex)
        return ex.status to messageResponse {
            error {
                text = "Internal Server Error. Please, contact system administrator."
            }
        }
    }

    private fun handle(ex: FileNotFoundException): Pair<HttpStatus, MessageResponse> {
        return HttpStatus.NOT_FOUND to messageResponse {
            error {
                code = ErrorCode.FILE_NOT_FOUND
                text = "File Not Found"
                details = listOf(ex.message)
            }
        }
    }

    private fun handle(ex: MethodArgumentTypeMismatchException, lang: Locale): Pair<HttpStatus, MessageResponse> {
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

    private fun handle(ex: DataAccessException, lang: Locale): Pair<HttpStatus, MessageResponse> {
        return HttpStatus.NOT_FOUND to messageResponse {
            error {
                code = ErrorCode.DATA_ACCESS_ERROR
                text = lang.localize("error.data.uncategorized")
                details = listOf(ex.message)
            }
        }
    }

    private fun handle(ex: EntityNotFoundException, lang: Locale): Pair<HttpStatus, MessageResponse> {
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

    private fun handle(ex: InvalidFormatException, lang: Locale): Pair<HttpStatus, MessageResponse> {
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

    private fun handle(ex: JsonProcessingException, lang: Locale): Pair<HttpStatus, MessageResponse> {
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

    private fun handle(ex: MethodArgumentNotValidException, lang: Locale): Pair<HttpStatus, MessageResponse> {
        return HttpStatus.BAD_REQUEST to messageResponse {
            error {
                code = ErrorCode.VALIDATION_FAILED
                text = lang.localize("error.validation.failed")
                details = errorMessagesGroupedByField(ex, lang)
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
                    field = lang.localize(field),
                    messages = messages.filterNotNull()
                )
            }
    }

    private fun Locale.localize(label: String, vararg args: Any?): String {
        return messages.getMessage(label, args, this)
    }

    private data class FieldErrorDetails(val field: String, val messages: List<String>)

    //    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler(MissingCsrfTokenException::class)
//    fun handleMissingCsrfToken(
//        ex: MissingCsrfTokenException,
//        lang: Locale
//    ): MessageResponse {
//
//        return messageResponse {
//            error {
//                code = ErrorCode.SECURITY_VIOLATION
//                text = lang.localize("error.csrf.missing")
//                details = listOf(
//                    lang.localize("error.csrf.missing.details")
//                )
//            }
//        }
//    }
//
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler(InvalidCsrfTokenException::class)
//    fun handleInvalidCsrfToken(
//        ex: InvalidCsrfTokenException,
//        lang: Locale
//    ): MessageResponse {
//
//        val (expected, actual) = ex
//
//        return messageResponse {
//            error {
//                code = ErrorCode.SECURITY_VIOLATION
//                text = lang.localize("error.csrf.invalid")
//                details = listOf(
//                    lang.localize("error.csrf.invalid.details", expected, actual)
//                )
//            }
//        }
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(AuthenticationException::class)
//    fun handleAccessDenied(
//        ex: AuthenticationException,
//        lang: Locale
//    ): MessageResponse {
//
//        return messageResponse {
//            error {
//                code = ErrorCode.SECURITY_VIOLATION
//                text = lang.localize("error.credentials.bad")
//            }
//        }
//    }
}
