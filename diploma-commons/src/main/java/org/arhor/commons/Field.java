package org.arhor.commons;

import org.arhor.commons.function.Accessor;
import org.arhor.commons.function.Mutator;

public abstract class Field<T, V> implements Accessor<T, V>, Mutator<T, V> {

  private final Class<V> valueType;

  public Field(Class<V> valueType) {
    this.valueType = valueType;
  }

  public final Class<V> getValueType() {
    return valueType;
  }
}
