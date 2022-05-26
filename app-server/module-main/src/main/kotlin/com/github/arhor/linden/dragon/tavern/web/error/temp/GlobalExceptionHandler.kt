package com.github.arhor.linden.dragon.tavern.web.error.temp

import com.github.arhor.linden.dragon.tavern.infrastructure.context.CurrentRequestContext
import com.github.arhor.linden.dragon.tavern.service.TimeService
import mu.KLogging
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.NoHandlerFoundException
import java.net.URI
import java.util.Locale
import java.util.TimeZone


@RestControllerAdvice
class GlobalExceptionHandler(
    private val messages: MessageSource,
    private val timeService: TimeService,
    private val currentRequestContext: CurrentRequestContext,
) {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleDefault(
        exception: Exception,
        locale: Locale,
        timeZone: TimeZone
    ): ApiError {
        logger.error("Unhandled exception. Consider appropriate exception handler", exception)
        return handleErrorCode(ErrorCode.UNCATEGORIZED, locale, timeZone)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(com.github.arhor.linden.dragon.tavern.exception.EntityNotFoundException::class)
    fun handleEntityNotFoundException(
        exception: com.github.arhor.linden.dragon.tavern.exception.EntityNotFoundException,
        locale: Locale,
        timeZone: TimeZone
    ): ApiError = handleErrorCode(ErrorCode.NOT_FOUND, locale, timeZone, exception.params)

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(com.github.arhor.linden.dragon.tavern.exception.EntityDuplicateException::class)
    fun handleEntityDuplicateException(
        exception: com.github.arhor.linden.dragon.tavern.exception.EntityDuplicateException,
        locale: Locale,
        timeZone: TimeZone
    ): ApiError = handleErrorCode(ErrorCode.DUPLICATE, locale, timeZone, exception.params)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        exception: MethodArgumentTypeMismatchException,
        locale: Locale,
        timeZone: TimeZone,
    ): ApiError = handleErrorCode(
        ErrorCode.METHOD_ARG_TYPE_MISMATCH,
        locale,
        timeZone,
        exception.name,
        exception.value,
        exception.requiredType
    )

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(
        exception: NoHandlerFoundException,
        locale: Locale,
        timeZone: TimeZone,
    ): Any = when {
        exception.requestURL == "/" -> {
            handleErrorCode(
                ErrorCode.HANDLER_NOT_FOUND_DEFAULT,
                locale,
                timeZone,
            )
        }
        exception.requestURL.startsWith("/api/") -> {
            handleErrorCode(
                ErrorCode.HANDLER_NOT_FOUND,
                locale,
                timeZone,
                exception.httpMethod,
                exception.requestURL,
            )
        }
        else -> {
            @Suppress("SpringMVCViewInspection")
            ModelAndView("forward:/")
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(locale: Locale, timeZone: TimeZone): ApiError {
        return handleErrorCode(ErrorCode.UNAUTHORIZED, locale, timeZone)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException,
        locale: Locale,
        timeZone: TimeZone,
    ): ApiError = handleErrorCode(
        error = ErrorCode.VALIDATION_FAILED,
        locale = locale,
        timeZone = timeZone,
        details = exception.bindingResult.let {
            val fieldErrors =
                handleObjectErrors(it.fieldErrors, FieldError::getField, FieldError::getDefaultMessage)
            val globalErrors =
                handleObjectErrors(it.globalErrors, ObjectError::getObjectName, ObjectError::getDefaultMessage)

            fieldErrors + globalErrors
        }
    )

    private fun handleErrorCode(
        error: ErrorCode,
        locale: Locale,
        timeZone: TimeZone,
        vararg args: Any?,
    ): ApiError {
        return handleErrorCode(error, locale, timeZone, emptyList(), *args)
    }

    private fun handleErrorCode(
        error: ErrorCode,
        locale: Locale,
        timeZone: TimeZone,
        details: List<String>,
        vararg args: Any?,
    ): ApiError = ApiError(
        requestId = currentRequestContext.requestId!!,
        timestamp = timeService.now(timeZone),
        message = messages.getMessage(error.label, args, locale),
        code = error,
        details = details,
    )

    private inline fun <T : ObjectError> handleObjectErrors(
        errors: List<T>,
        nameProvider: (T) -> String,
        messageProvider: (T) -> String?,
    ): List<String> {
        val result = ArrayList<String>(errors.size)

        for (error in errors) {
            val name = nameProvider(error)
            val message = messageProvider(error)

            result.add("$name: $message")
        }
        return result
    }

//    private fun handleResponseStatusException(ex: ResponseStatusException): Pair<HttpStatus, MessageResponse> {
//        logger.error("Unhandled error. Please, create proper exception handler for it.", ex)
//        return ex.status to messageResponse {
//            error {
//                text = "Internal Server Error. Please, contact system administrator."
//            }
//        }
//    }
//
//    private fun handleFileNotFoundException(ex: FileNotFoundException): Pair<HttpStatus, MessageResponse> {
//        return HttpStatus.NOT_FOUND to messageResponse {
//            error {
//                code = ErrorCode.FILE_NOT_FOUND
//                text = "File Not Found"
//                details = listOf(ex.message)
//            }
//        }
//    }
//
//    private fun handleMethodArgumentTypeMismatchException(
//        ex: MethodArgumentTypeMismatchException,
//        lang: Locale
//    ): Pair<HttpStatus, MessageResponse> {
//        return HttpStatus.BAD_REQUEST to messageResponse {
//            error {
//                code = ErrorCode.UNCATEGORIZED
//                text = lang.localize("error.wrong.argument")
//                details = listOf(
//                    lang.localize("error.wrong.argument.details", ex.name, ex.value)
//                )
//            }
//        }
//    }
//
//    private fun handleDataAccessException(ex: DataAccessException, lang: Locale): Pair<HttpStatus, MessageResponse> {
//        return HttpStatus.NOT_FOUND to messageResponse {
//            error {
//                code = ErrorCode.DATA_ACCESS_ERROR
//                text = lang.localize("error.data.uncategorized")
//                details = listOf(ex.message)
//            }
//        }
//    }
//
//    private fun handleEntityNotFoundException(
//        ex: EntityNotFoundException,
//        lang: Locale
//    ): Pair<HttpStatus, MessageResponse> {
//        val (entityType, propName, propValue) = ex
//
//        return HttpStatus.NOT_FOUND to messageResponse {
//            error {
//                code = ErrorCode.NOT_FOUND
//                text = lang.localize("error.entity.notfound")
//                details = listOf(
//                    lang.localize("error.entity.notfound.details", entityType, propName, propValue)
//                )
//            }
//        }
//    }
//
//    private fun handleInvalidFormatException(
//        ex: InvalidFormatException,
//        lang: Locale
//    ): Pair<HttpStatus, MessageResponse> {
//        val parser = ex.processor as JsonParser
//
//        return HttpStatus.BAD_REQUEST to messageResponse {
//            error {
//                code = ErrorCode.VALIDATION_FAILED
//                text = lang.localize("error.json.format.invalid")
//                details = listOf(
//                    lang.localize(
//                        "error.json.format.invalid.details",
//                        ex.value,
//                        parser.currentName,
//                        ex.targetType.simpleName
//                    )
//                )
//            }
//        }
//    }
//
//    private fun handleJsonProcessingException(
//        ex: JsonProcessingException,
//        lang: Locale
//    ): Pair<HttpStatus, MessageResponse> {
//        val value = when (val processor = ex.processor) {
//            is JsonParser -> processor.currentName ?: processor.text
//            else -> "[UNKNOWN JSON PROCESSOR]"
//        }
//
//        return HttpStatus.BAD_REQUEST to messageResponse {
//            error {
//                code = ErrorCode.UNCATEGORIZED
//                text = lang.localize("error.json.parse")
//                details = listOf(
//                    lang.localize(
//                        "error.json.parse.details",
//                        value,
//                        ex.location.lineNr,
//                        ex.location.columnNr
//                    )
//                )
//            }
//        }
//    }
//
//    private fun handleMethodArgumentNotValidException(
//        ex: MethodArgumentNotValidException,
//        lang: Locale
//    ): Pair<HttpStatus, MessageResponse> {
//        return HttpStatus.BAD_REQUEST to messageResponse {
//            error {
//                code = ErrorCode.VALIDATION_FAILED
//                text = lang.localize("Object validation failed")
//                details = errorMessagesGroupedByField(ex, lang)
//            }
//        }
//    }
//
//    private fun handleAuthenticationException(
//        ex: AuthenticationException,
//        lang: Locale
//    ): Pair<HttpStatus, MessageResponse> {
//        logger.error { "Authentication failed: ${ex.message}" }
//        return HttpStatus.UNAUTHORIZED to messageResponse {
//            error {
//                code = ErrorCode.SECURITY_VIOLATION
//                text = lang.localize("error.credentials.bad")
//            }
//        }
//    }
//
//    private fun errorMessagesGroupedByField(
//        ex: MethodArgumentNotValidException,
//        lang: Locale
//    ): List<FieldErrorDetails> {
//
//        return ex.allErrors
//            .map { it as FieldError }
//            .groupBy({ it.field }, { it.defaultMessage })
//            .map { (field, messages) ->
//                FieldErrorDetails(
//                    field = lang.localize("error.validation.failed", field),
//                    messages = messages.filterNotNull()
//                )
//            }
//    }

    companion object : KLogging()
}
