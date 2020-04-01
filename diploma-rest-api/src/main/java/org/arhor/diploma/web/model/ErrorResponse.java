package org.arhor.diploma.web.model;

import java.util.List;

public final class ErrorResponse {

  private final List<ApiError> errors;

  private ErrorResponse(List<ApiError> errors) {
    this.errors = errors;
  }

  public static ErrorResponse of(ApiError e1) {
    return new ErrorResponse(List.of(e1));
  }

  public static ErrorResponse of(ApiError e1, ApiError e2) {
    return new ErrorResponse(List.of(e1, e2));
  }

  public static ErrorResponse of(ApiError e1, ApiError e2, ApiError e3) {
    return new ErrorResponse(List.of(e1, e2, e3));
  }

  public static ErrorResponse of(ApiError e1, ApiError e2, ApiError e3, ApiError e4) {
    return new ErrorResponse(List.of(e1, e2, e3, e4));
  }

  public static ErrorResponse of(ApiError e1, ApiError e2, ApiError e3, ApiError e4, ApiError e5) {
    return new ErrorResponse(List.of(e1, e2, e3, e4, e5));
  }

  public static ErrorResponse of(ApiError... errors) {
    return new ErrorResponse(List.of(errors));
  }

  public List<ApiError> getErrors() {
    return errors;
  }
}
