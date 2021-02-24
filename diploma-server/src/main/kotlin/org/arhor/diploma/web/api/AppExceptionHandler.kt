package org.arhor.diploma.web.api

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.arhor.diploma.exception.EntityNotFoundException
import org.arhor.diploma.exception.InvalidCsrfTokenException
import org.arhor.diploma.exception.MissingCsrfTokenException
import org.arhor.diploma.web.model.MessageResponse
import org.arhor.diploma.web.model.messageResponse
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*


@RestControllerAdvice
class AppExceptionHandler(
    private val messageSource: MessageSource
) : ResponseEntityExceptionHandler() {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleArgumentClassCastException(e: MethodArgumentTypeMismatchException, lang: Locale): MessageResponse {
        return messageResponse {
            error {
                code = 400
                text = lang.localize("error.wrong.argument")
                details = listOf(
                    lang.localize("error.wrong.argument.details", e.name, e.value)
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MissingCsrfTokenException::class)
    fun handleMissingCsrfTokenException(e: MissingCsrfTokenException, lang: Locale): MessageResponse {
        return messageResponse {
            error {
                code = 403
                text = lang.localize("error.csrf.missing")
                details = listOf(
                    lang.localize("error.csrf.missing.details")
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidCsrfTokenException::class)
    fun handleInvalidCsrfTokenException(ex: InvalidCsrfTokenException, lang: Locale): MessageResponse {
        val (expected, actual) = ex
        return messageResponse {
            error {
                code = 403
                text = lang.localize("error.csrf.invalid")
                details = listOf(
                    lang.localize("error.csrf.invalid.details", expected, actual)
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException, lang: Locale): MessageResponse {
        val (entityType, propName, propValue) = ex
        return messageResponse {
            error {
                code = 404
                text = lang.localize("error.entity.notfound")
                details = listOf(
                    lang.localize("error.entity.notfound.details", entityType, propName, propValue)
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException::class)
    fun handleInvalidFormatException(e: InvalidFormatException, lang: Locale): MessageResponse {
        val parser = e.processor as JsonParser
        return messageResponse {
            error {
                code = 400
                text = lang.localize("error.json.format.invalid")
                details = listOf(
                    lang.localize(
                        "error.json.format.invalid.details",
                        e.value,
                        parser.currentName,
                        e.targetType.simpleName
                    )
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonProcessingException::class)
    fun handleJsonProcessingException(e: JsonProcessingException, lang: Locale): MessageResponse {
        val value = when (val processor = e.processor) {
            is JsonParser -> processor.currentName ?: processor.text
            else -> "[UNKNOWN JSON PROCESSOR]"
        }
        return messageResponse {
            error {
                code = 400
                text = lang.localize("error.json.parse")
                details = listOf(
                    lang.localize("error.json.parse.details", value, e.location.lineNr, e.location.columnNr)
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException::class)
    fun handleAccessDeniedException(ex: AuthenticationException, lang: Locale): MessageResponse {
        return messageResponse {
            error {
                code = 401
                text = lang.localize("error.credentials.bad")
                details = ex.message?.let { listOf(it) } ?: emptyList()
            }
        }
    }

    private fun Locale.localize(label: String, vararg args: Any?): String {
        return messageSource.getMessage(label, args, this)
    }
}
