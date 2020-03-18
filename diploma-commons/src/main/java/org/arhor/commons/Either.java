package org.arhor.commons;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Function;

public final class Either<T, E> {

  private static final Either<?, ?> SUCCESS = new Either<>();
  private static final Either<?, ?> FAILURE = new Either<>();

  private final T value;
  private final E error;

  private Either(T value, E error) {
    this.value = value;
    this.error = error;
  }

  private Either() {
    this(null, null);
  }

  @SuppressWarnings("unchecked")
  public static <E> Either<Void, E> success() {
    return (Either<Void, E>) SUCCESS;
  }

  @SuppressWarnings("unchecked")
  public static <E> Either<Void, E> failure() {
    return (Either<Void, E>) FAILURE;
  }

  public static <T, E> Either<T, E> success(T item) {
    return new Either<>(item, null);
  }

  public static <T, E> Either<T, E> failure(E error) {
    return new Either<>(null, error);
  }

  public final boolean hasError() {
    return error != null;
  }

  public final Optional<T> value() {
    if (hasError()) {
      throw new IllegalStateException("Must not extract item in error state");
    }
    return Optional.ofNullable(value);
  }

  public final E error() {
    return error;
  }

  public <R> Either<R, E> map(@Nonnull Function<T, R> mapper) {
    return new Either<>(hasError() ? null : mapper.apply(value), error);
  }
}
