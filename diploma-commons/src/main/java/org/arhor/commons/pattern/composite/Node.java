package org.arhor.commons.pattern.composite;

import javax.annotation.Nonnull;

public interface Node<T> extends Composite<T> {

  Node<T> add(@Nonnull Composite<T> child);

  @SafeVarargs
  static <T> Node<T> node(@Nonnull Composite<T>... children) {
    return Internals.buildNode(children);
  }
}
