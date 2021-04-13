package org.arhor.diploma.web.api

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import mu.KotlinLogging
import org.arhor.diploma.commons.data.DataAccessException
import org.arhor.diploma.commons.data.EntityNotFoundException
import org.arhor.diploma.exception.InvalidCsrfTokenException
import org.arhor.diploma.exception.MissingCsrfTokenException
import org.arhor.diploma.web.model.ErrorCode
import org.arhor.diploma.web.model.MessageResponse
import org.arhor.diploma.web.model.messageResponse
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.FileNotFoundException
import java.util.*

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class AppExceptionHandler(
    private val messageSource: MessageSource,
) : ResponseEntityExceptionHandler() {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    fun defaultExceptionHandler(ex: Throwable, lang: Locale): MessageResponse {

        log.error("Unhandled error. Please, create proper exception handler for it.", ex)

        return messageResponse {
            error {
                text = "Internal Server Error. Please, contact system administrator."
            }
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotFoundException::class)
    fun handleFileNotFound(ex: FileNotFoundException, lang: Locale): MessageResponse {

        return messageResponse {
            error {
                code = ErrorCode.FILE_NOT_FOUND
                text = "File Not Found"
                details = listOf(ex.message)
            }
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleArgumentClassCast(ex: MethodArgumentTypeMismatchException, lang: Locale): MessageResponse {

        return messageResponse {
            error {
                code = ErrorCode.UNCATEGORIZED
                text = lang.localize("error.wrong.argument")
                details = listOf(
                    lang.localize("error.wrong.argument.details", ex.name, ex.value)
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MissingCsrfTokenException::class)
    fun handleMissingCsrfToken(
        ex: MissingCsrfTokenException,
        lang: Locale
    ): MessageResponse {

        return messageResponse {
            error {
                code = ErrorCode.SECURITY_VIOLATION
                text = lang.localize("error.csrf.missing")
                details = listOf(
                    lang.localize("error.csrf.missing.details")
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidCsrfTokenException::class)
    fun handleInvalidCsrfToken(
        ex: InvalidCsrfTokenException,
        lang: Locale
    ): MessageResponse {

        val (expected, actual) = ex

        return messageResponse {
            error {
                code = ErrorCode.SECURITY_VIOLATION
                text = lang.localize("error.csrf.invalid")
                details = listOf(
                    lang.localize("error.csrf.invalid.details", expected, actual)
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataAccessException::class)
    fun handleDataAccess(
        ex: DataAccessException,
        lang: Locale
    ): MessageResponse {
        return messageResponse {
            error {
                code = ErrorCode.DATA_ACCESS_ERROR
                text = lang.localize("error.data.uncategorized")
            }
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFound(
        ex: EntityNotFoundException,
        lang: Locale
    ): MessageResponse {

        val (entityType, propName, propValue) = ex

        return messageResponse {
            error {
                code = ErrorCode.NOT_FOUND
                text = lang.localize("error.entity.notfound")
                details = listOf(
                    lang.localize("error.entity.notfound.details", entityType, propName, propValue)
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException::class)
    fun handleInvalidFormat(
        ex: InvalidFormatException,
        lang: Locale
    ): MessageResponse {

        val parser = ex.processor as JsonParser

        return messageResponse {
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonProcessingException::class)
    fun handleJsonProcessing(
        ex: JsonProcessingException,
        lang: Locale
    ): MessageResponse {

        val value = when (val processor = ex.processor) {
            is JsonParser -> processor.currentName ?: processor.text
            else -> "[UNKNOWN JSON PROCESSOR]"
        }

        return messageResponse {
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

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException::class)
    fun handleAccessDenied(
        ex: AuthenticationException,
        lang: Locale
    ): MessageResponse {

        return messageResponse {
            error {
                code = ErrorCode.SECURITY_VIOLATION
                text = lang.localize("error.credentials.bad")
            }
        }
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        val lang = request.locale

        return handleExceptionInternal(
            ex,
            messageResponse {
                error {
                    code = ErrorCode.VALIDATION_FAILED
                    text = lang.localize("error.validation.failed")
                    details = errorMessagesGroupedByField(ex, lang)
                }
            },
            headers,
            status,
            request
        )
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
        return messageSource.getMessage(label, args, this)
    }

    private data class FieldErrorDetails(val field: String, val messages: List<String>)
}
