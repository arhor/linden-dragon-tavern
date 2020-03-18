package org.arhor.commons.pattern.composite;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

final class Internals {

  private Internals() { throw new UnsupportedOperationException("Must not be instantiated"); }

  @SafeVarargs
  static <T> Node<T> buildNode(Composite<T>... children) {
    return new NodeImpl<>(children);
  }

  static <T> Leaf<T> buildLeaf(T value) {
    return new LeafImpl<>(value);
  }

  private static final class NodeImpl<T> implements Node<T> {

    @Nonnull
    private final List<Composite<T>> children;

    @SafeVarargs
    NodeImpl(final Composite<T>... children) {
      this.children = Arrays.stream(children)
          .filter(Objects::nonNull)
          .collect(toList());
    }

    @Override
    public final Node<T> add(@Nonnull final Composite<T> child) {
      children.add(child);
      return this;
    }

    @Override
    public final void execute(@Nonnull final Consumer<T> action) {
      for (final Composite<T> child : children) {
        child.execute(action);
      }
    }
  }

  private static final class LeafImpl<T> implements Leaf<T> {

    @Nullable
    private T value;

    LeafImpl(@Nullable final T value) {
      this.value = value;
    }

    @Nullable
    @Override
    public final T getValue() {
      return value;
    }

    @Override
    public final void setValue(@Nullable final T value) {
      this.value = value;
    }

    @Override
    public final void execute(@Nonnull final Consumer<T> action) {
      action.accept(value);
    }
  }
}
