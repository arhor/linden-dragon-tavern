package org.arhor.diploma.web.api

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
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
import java.util.*

@RestControllerAdvice
class AppExceptionHandler(
    private val messageSource: MessageSource,
) : ResponseEntityExceptionHandler() {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Throwable::class)
    fun defaultExceptionHandler(ex: Throwable, lang: Locale): MessageResponse {

        return messageResponse {
            error {
                code = ErrorCode.UNCATEGORIZED
                text = "Unhandled error. Please, create proper exception handler for it."
                details = ex.message?.let { listOf(it) } ?: emptyList()
            }
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleArgumentClassCastException(
        ex: MethodArgumentTypeMismatchException,
        lang: Locale
    ): MessageResponse {

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
    fun handleMissingCsrfTokenException(
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
    fun handleInvalidCsrfTokenException(
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
    fun handleDataAccessException(
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
    fun handleEntityNotFoundException(
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
    fun handleInvalidFormatException(
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
    fun handleJsonProcessingException(
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
    fun handleAccessDeniedException(
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

        val locale = request.locale

        return handleExceptionInternal(
            ex,
            messageResponse {
                error {
                    code = ErrorCode.VALIDATION_FAILED
                    text = locale.localize("error.validation.failed")
                    details = errorMessagesGroupedByField(ex, locale)
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
