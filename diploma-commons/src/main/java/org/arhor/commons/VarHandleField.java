package org.arhor.commons;

import java.lang.invoke.VarHandle;

public final class VarHandleField<T, V> extends Field<T, V> {

  private final VarHandle varHandle;

  public VarHandleField(Class<V> valueType, VarHandle varHandle) {
    super(valueType);
    this.varHandle = varHandle;
  }

  @Override
  @SuppressWarnings("unchecked")
  public V access(T item) {
    return (V) varHandle.get(item);
  }

  @Override
  public void mutate(T item, V value) {
    varHandle.set(item, value);
  }
}
