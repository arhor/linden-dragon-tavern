package org.arhor.diploma.web.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.arhor.diploma.web.model.MessageResponse
import org.arhor.diploma.web.model.messageResponse
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.error.ErrorAttributeOptions.Include.STACK_TRACE
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest

@RestController
class CustomErrorController(private val mapper: ObjectMapper, attrs: ErrorAttributes?) : ErrorController {

  private val attributes: ErrorAttributes = attrs ?: throw IllegalArgumentException("ErrorAttributes must not be null")

  override fun getErrorPath(): String = ERROR_PATH

  @RequestMapping(ERROR_PATH)
  fun error(req: WebRequest): MessageResponse {
    return messageResponse {
      error {
        code = 500
        text = "Unhandled error. Please, create proper exception handler for it."
        details = listOf(req.errorAttributes)
      }
    }
  }

  private inline val WebRequest.errorAttributes: Map<String, Any?>
    get() {
      val includeStackTrace = this.getParameter("trace")?.toLowerCase() != "false"

      val springErrorAttributes = attributes.getErrorAttributes(
          this,
          errorOptions {
            if (includeStackTrace) {
              including(STACK_TRACE)
            }
          }
      )

      for ((key, value) in springErrorAttributes) {
        if (key == "trace" && value is String) {
          springErrorAttributes[key] = value.split("\n\t").toTypedArray()
        }
      }

      return springErrorAttributes.toMap()
    }

  private inline fun errorOptions(init: ErrorAttributeOptions.() -> Unit): ErrorAttributeOptions {
    val options = ErrorAttributeOptions.defaults()
    options.init()
    return options
  }

  companion object {
    const val ERROR_PATH = "/api/error"
  }
}