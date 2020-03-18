package org.arhor.commons.pattern.composite;

import javax.annotation.Nullable;

public interface Leaf<T> extends Composite<T> {

  @Nullable
  T getValue();

  void setValue(@Nullable T value);

  static <T> Leaf<T> leaf(T value) {
    return Internals.buildLeaf(value);
  }
}
