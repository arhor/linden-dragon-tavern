package org.arhor.diploma.exception;

public class EntityNotFoundException extends RuntimeException {

  private final Class<?> entityClass;
  private final String fieldName;
  private final Object fieldValue;

  public EntityNotFoundException(Class<?> entityClass, String fieldName, Object fieldValue) {
    this.entityClass = entityClass;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

  public Class<?> getEntityClass() {
    return entityClass;
  }

  public String getFieldName() {
    return fieldName;
  }

  public Object getFieldValue() {
    return fieldValue;
  }
}
