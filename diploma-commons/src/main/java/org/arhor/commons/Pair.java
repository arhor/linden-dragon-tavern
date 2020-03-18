package org.arhor.commons;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class Pair<S, T> {

  private final @Nonnull S first;
  private final @Nonnull T second;

  private Pair(@Nonnull S first, @Nonnull T second) {
    this.first = first;
    this.second = second;
  }

  @Nonnull
  public static
  <S, T> Pair<S, T> of(S first, T second) {
    return new Pair<>(first, second);
  }

  @Nonnull
  public static
  <S, T> Collector<Pair<S, T>, ?, Map<S, T>> toMap() {
    return Collectors.toMap(Pair::getFirst, Pair::getSecond);
  }

  @Nonnull
  public S getFirst() {
    return first;
  }

  @Nonnull
  public T getSecond() {
    return second;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Pair<?, ?> pair = (Pair<?, ?>) o;
    return first.equals(pair.first)
        && second.equals(pair.second);
  }

  @Override
  public int hashCode() {
    return Objects.hash(first, second);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", "{ " + Pair.class.getSimpleName() + ": {", " }")
        .add("first: " + first)
        .add("second: " + second)
        .toString();
  }
}
