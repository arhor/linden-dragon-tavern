package org.arhor.diploma.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class EntityNotFoundException extends RuntimeException {

  private final String className;
  private final String fieldName;
  private final Object fieldValue;

}
