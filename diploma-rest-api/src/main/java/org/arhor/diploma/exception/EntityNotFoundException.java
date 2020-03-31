package org.arhor.diploma.exception;

public class EntityNotFoundException extends RuntimeException {

  private final String className;
  private final String fieldName;
  private final Object fieldValue;

  public EntityNotFoundException(String className, String fieldName, Object fieldValue) {
    this.className = className;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

  public String getClassName() {
    return className;
  }

  public String getFieldName() {
    return fieldName;
  }

  public Object getFieldValue() {
    return fieldValue;
  }
}
