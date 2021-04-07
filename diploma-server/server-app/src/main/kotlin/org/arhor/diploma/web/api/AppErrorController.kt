package org.arhor.diploma.web.api

import mu.KotlinLogging
import org.arhor.diploma.web.model.messageResponse
import org.springframework.boot.autoconfigure.web.ErrorProperties
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeAttribute
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import javax.servlet.RequestDispatcher

private const val DEFAULT_ERROR_PATH = "/api/error"
private const val ERROR_PATH = "\${server.error.path:\${error.path:$DEFAULT_ERROR_PATH}}"

private val logger = KotlinLogging.logger {}

@RestController
class AppErrorController(private val errorAttrs: ErrorAttributes) : ErrorController {

    private val errorProps = ErrorProperties()

    override fun getErrorPath(): String = ERROR_PATH

    @RequestMapping(ERROR_PATH)
    fun error(req: WebRequest): ResponseEntity<*> {
        val status = getHttpStatus(req)

        if (status == HttpStatus.NO_CONTENT) {
            return ResponseEntity<Nothing>(status)
        }

        return ResponseEntity(
            messageResponse {
                error {
                    text = "Unhandled error. Please, create proper exception handler for it."
                    details = listOf(errorAttributes(req))
                }
            }, status
        )
    }

    private fun errorAttributes(req: WebRequest): Map<String, Any?> {
        val springErrorAttributes = errorAttrs.getErrorAttributes(
            req,
            getErrorAttributeOptions(req)
        )

        logger.debug("current error-attributes: {}", springErrorAttributes)

        for ((key, value) in springErrorAttributes) {
            if (key == "trace" && value is String) {
                springErrorAttributes[key] = value.split("\n\t").toTypedArray()
            }
        }
        return springErrorAttributes.toMap()
    }

    private fun getHttpStatus(req: WebRequest): HttpStatus {
        val statusCode = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE, WebRequest.SCOPE_REQUEST) as Int?
            ?: return HttpStatus.INTERNAL_SERVER_ERROR

        return try {
            HttpStatus.valueOf(statusCode)
        } catch (ex: Exception) {
            HttpStatus.INTERNAL_SERVER_ERROR
        }
    }

    private fun getErrorAttributeOptions(req: WebRequest): ErrorAttributeOptions {
        var options = ErrorAttributeOptions.defaults()
        if (errorProps.isIncludeException) {
            options = options.including(ErrorAttributeOptions.Include.EXCEPTION)
        }
        if (isIncludeStackTrace(req)) {
            options = options.including(ErrorAttributeOptions.Include.STACK_TRACE)
        }
        if (isIncludeMessage(req)) {
            options = options.including(ErrorAttributeOptions.Include.MESSAGE)
        }
        if (isIncludeBindingErrors(req)) {
            options = options.including(ErrorAttributeOptions.Include.BINDING_ERRORS)
        }
        return options
    }

    private fun isIncludeMessage(req: WebRequest): Boolean {
        return isIncludeAttribute(errorProps.includeMessage) {
            getBooleanParameter(req, "message")
        }
    }

    private fun isIncludeBindingErrors(req: WebRequest): Boolean {
        return isIncludeAttribute(errorProps.includeBindingErrors) {
            getBooleanParameter(req, "errors")
        }
    }

    private inline fun isIncludeAttribute(shouldInclude: IncludeAttribute, booleanSource: () -> Boolean): Boolean {
        return when (shouldInclude) {
            IncludeAttribute.ALWAYS -> true
            IncludeAttribute.ON_PARAM -> booleanSource()
            else -> false
        }
    }

    private fun isIncludeStackTrace(req: WebRequest): Boolean {
        return when (errorProps.includeStacktrace) {
            IncludeStacktrace.ALWAYS -> true
            IncludeStacktrace.ON_PARAM -> getBooleanParameter(req, "trace")
            else -> false
        }
    }

    private fun getBooleanParameter(req: WebRequest, param: String): Boolean {
        return req.getParameter(param)?.equals("true", ignoreCase = true) ?: false
    }
}