package org.arhor.diploma.web.api

import com.fasterxml.jackson.core.JsonLocation
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.arhor.diploma.exception.EntityNotFoundException
import org.arhor.diploma.exception.InvalidCsrfTokenException
import org.arhor.diploma.exception.MissingCsrfTokenException
import org.arhor.diploma.util.createLogger
import org.arhor.diploma.web.model.MessageResponse
import org.arhor.diploma.web.model.messageResponse
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.io.IOException
import java.util.*
import kotlin.jvm.Throws

@RestControllerAdvice
class ExceptionController(
    private val messageSource: MessageSource
) : ResponseEntityExceptionHandler() {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun argumentClassCastException(e: MethodArgumentTypeMismatchException, lang: Locale): MessageResponse {
        log.error("Argument class cast exception", e)
        return messageResponse {
            error {
                code = 400
                text = localize(lang, "error.wrong.argument")
                details = listOf(
                    localize(lang, "error.wrong.argument.details", e.name, e.value)
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MissingCsrfTokenException::class)
    fun missingCsrfTokenException(e: MissingCsrfTokenException, lang: Locale): MessageResponse {
        log.error(e.message, e)
        return messageResponse {
            error {
                code = 403
                text = localize(lang, "error.csrf.missing")
                details = listOf(
                    localize(lang, "error.csrf.missing.details")
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidCsrfTokenException::class)
    fun invalidCsrfTokenException(e: InvalidCsrfTokenException, lang: Locale): MessageResponse {
        log.error(e.message, e)
        return messageResponse {
            error {
                code = 403
                text = localize(lang, "error.csrf.invalid")
                details = listOf(
                    localize(lang, "error.csrf.invalid.details", e.expected, e.actual)
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    fun entityNotFoundException(e: EntityNotFoundException, lang: Locale): MessageResponse {
        log.error("Resource not found", e)
        return messageResponse {
            error {
                code = 404
                text = localize(lang, "error.entity.notfound")
                details = listOf(
                    localize(lang, "error.entity.notfound.details", e.className, e.fieldName, e.fieldValue)
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun mediaTypeNotSupportedException(e: HttpMediaTypeNotSupportedException, lang: Locale): MessageResponse {
        log.error("Request media type not supported", e)
        return messageResponse {
            error {
                code = 415
                text = localize(lang, "error.type.unsupported")
                details = listOf(
                    localize(lang, "error.type.unsupported.details", e.contentType, e.supportedMediaTypes)
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(HttpMediaTypeNotAcceptableException::class)
    fun mediaTypeNotAcceptableException(e: HttpMediaTypeNotAcceptableException, lang: Locale): MessageResponse {
        log.error("Media type not accepted", e)
        return messageResponse {
            error {
                code = 406
                text = localize(lang, "error.type.unaccepted")
                details = listOf(
                    localize(lang, "error.type.unaccepted.details", e.supportedMediaTypes)
                )
            }
        }
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun requestMethodNotSupported(e: HttpRequestMethodNotSupportedException, lang: Locale): MessageResponse {
        log.error("Request method not supported", e)
        return messageResponse {
            error {
                code = 405
                text = localize(lang, "error.method.unsupported")
                details = listOf(
                    localize(lang, "error.method.unsupported.details", e.method, e.supportedHttpMethods)
                )
            }
        }
    }

    @Throws(IOException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException::class)
    fun invalidFormatException(e: InvalidFormatException, lang: Locale): MessageResponse {
        log.error("Invalid JSON message", e)
        val parser = e.processor as JsonParser

        val propertyValue = e.value
        val propertyName = parser.currentName
        val targetType = e.targetType.simpleName

        return messageResponse {
            error {
                code = 400
                text = localize(lang, "error.json.format.invalid")
                details = listOf(
                    localize(lang, "error.json.format.invalid.details", propertyValue, propertyName, targetType)
                )
            }
        }
    }

    @Throws(IOException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonProcessingException::class)
    fun jsonProcessingException(e: JsonProcessingException, lang: Locale): MessageResponse {
        log.error("JSON processing exception", e)
        val value = when (val processor = e.processor) {
            is JsonParser -> processor.currentName ?: processor.text
            else -> "[UNKNOWN JSON PROCESSOR]"
        }
        val location = e.location
        return messageResponse {
            error {
                code = 400
                text = localize(lang, "error.json.parse")
                details = listOf(
                    localize(lang, "error.json.parse.details", value, location.lineNr, location.columnNr)
                )
            }
        }
    }

    private fun localize(lang: Locale, label: String, vararg args: Any?): String {
        return messageSource.getMessage(label, args, lang)
    }

    companion object {
        @JvmStatic
        private val log = createLogger<ExceptionController>()
    }
}
