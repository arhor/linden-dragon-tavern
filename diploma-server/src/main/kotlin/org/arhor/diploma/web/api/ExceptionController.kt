package org.arhor.diploma.web.api

import org.arhor.diploma.web.model.MessageResponse
import org.arhor.diploma.web.model.messageResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@RestControllerAdvice
class ExceptionController(
    private val messageSource: MessageSource
) : ResponseEntityExceptionHandler() {

  companion object {
    @JvmStatic
    private val log: Logger = LoggerFactory.getLogger(ExceptionController::class.java)
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentTypeMismatchException::class)
  fun argumentClassCastException(e: MethodArgumentTypeMismatchException, l: Locale): MessageResponse {
    log.error("Argument class cast exception", e)
    return messageResponse {
      error {
        code = 400
        text = localize(l, "error.wrong.argument")
        details = listOf(
            localize(l, "error.wrong.argument.details", e.name, e.value)
        )
      }
    }
  }

  private fun localize(l: Locale, label: String, vararg args: Any?): String {
    return messageSource.getMessage(label, args, l)
  }

//  @ResponseStatus(HttpStatus.NOT_FOUND)
//  @ExceptionHandler(EntityNotFoundException.class)
//  public ApiError entityNotFoundException(EntityNotFoundException ex, WebRequest request) {
//    log.error("Resource not found", ex);
//    return new ApiError(
//        ApiError.NOT_FOUND,
//        messageSource.getMessage(
//            ex.getErrorLabel().getValue(),
//            new Object[]{ ex.getFieldName(), ex.getFieldValue() },
//            request.getLocale()
//        )
//    );
//  }
//
//  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//  public ApiError mediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, WebRequest request) {
//    log.error("Request media type not supported", ex);
//    return new ApiError(
//        ApiError.TYPE_UNSUPPORTED,
//        messageSource.getMessage(
//            "error.type.unsupported",
//            new Object[] { ex.getContentType() },
//            request.getLocale()
//        )
//    );
//  }
//
//  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
//  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
//  public String mediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex, WebRequest request) {
//    log.error("Media type not accepted", ex);
//    return new ApiError(
//        ApiError.TYPE_NOT_ACCEPTED,
//        messageSource.getMessage(
//            "error.type.unaccepted",
//            new Object[] { ex.getSupportedMediaTypes() },
//            request.getLocale()
//        )
//    ).toString();
//  }
//
//  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
//  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//  public ApiError requestMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
//    log.error("Request method not supported", ex);
//    return new ApiError(
//        ApiError.METHOD_UNSUPPORTED,
//        messageSource.getMessage(
//            "error.method.unsupported",
//            new Object[] { ex.getMethod(), ex.getSupportedHttpMethods() },
//            request.getLocale()
//        )
//    );
//  }
//
//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  @ExceptionHandler(InvalidFormatException.class)
//  public ApiError invalidFormatException(InvalidFormatException ex, WebRequest request) throws IOException {
//    log.error("Invalid JSON message", ex);
//    JsonParser parser = (JsonParser) ex.getProcessor();
//
//    Object value = ex.getValue();
//    String fieldName = parser.getCurrentName();
//    String targetType = ex.getTargetType().getSimpleName();
//
//    return new ApiError(
//        ApiError.INVALID_JSON_MESSAGE,
//        messageSource.getMessage(
//            "error.json.invalid",
//            new Object[] { value, fieldName, targetType },
//            request.getLocale()
//        )
//    );
//  }
//
//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  @ExceptionHandler(JsonProcessingException.class)
//  public ApiError jsonProcessingException(JsonProcessingException ex, WebRequest request) throws IOException {
//    log.error("JSON processing exception", ex);
//
//    var processor = ex.getProcessor();
//
//    String value;
//    if (processor instanceof JsonParser) {
//      final var parser = (JsonParser) processor;
//      value = (parser.getCurrentName() != null) ? parser.getCurrentName() : parser.getText();
//    } else {
//      value = "[UNKNOWN JSON PROCESSOR]";
//    }
//
//    JsonLocation loc = ex.getLocation();
//
//    return new ApiError(
//        ApiError.INVALID_JSON_MESSAGE,
//        messageSource.getMessage(
//            "error.json.parse",
//            new Object[] { value, loc.getLineNr(), loc.getColumnNr() },
//            request.getLocale()
//        )
//    );
//  }
}
