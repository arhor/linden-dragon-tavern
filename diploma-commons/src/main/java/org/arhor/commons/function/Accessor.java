package org.arhor.commons.function;

import java.util.function.Function;

@FunctionalInterface
public interface Accessor<T, R> extends Function<T, R> {

  R access(T item);

  @Override
  default R apply(T item) {
    return access(item);
  }
}
