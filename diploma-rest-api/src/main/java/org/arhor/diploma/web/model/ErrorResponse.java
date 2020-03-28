package org.arhor.diploma.web.model;

import java.util.List;

public final class ErrorResponse {

  private final List<ApiError> errors;

  public ErrorResponse(List<ApiError> errors) {
    this.errors = errors != null ? List.copyOf(errors) : List.of();
  }

  public List<ApiError> getErrors() {
    return errors;
  }
}
