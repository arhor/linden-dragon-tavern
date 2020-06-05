package org.arhor.commons.function;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface Mutator<T, V> extends BiConsumer<T, V> {

  void mutate(T item, V value);

  @Override
  default void accept(T item, V value) {
    mutate(item, value);
  }
}
