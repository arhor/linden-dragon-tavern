package org.arhor.commons;

import org.arhor.commons.function.Accessor;
import org.arhor.commons.function.Mutator;

public final class MethodRefField<T, V> extends Field<T, V> {

  private final Accessor<T, V> accessor;
  private final Mutator<T, V> mutator;

  public MethodRefField(Class<V> valueType, Accessor<T, V> accessor, Mutator<T, V> mutator) {
    super(valueType);
    this.accessor = accessor;
    this.mutator = mutator;
  }

  @Override
  public V access(T item) {
    return accessor.access(item);
  }

  @Override
  public void mutate(T item, V value) {
    mutator.mutate(item, value);
  }
}
