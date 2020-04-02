package org.arhor.commons.pattern.lazy;

import org.arhor.commons.function.RichSupplier;

import javax.annotation.Nonnull;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Lazy<T> extends RichSupplier<T> {

  /**
   * Method shows is this lazy instance already computed or not.
   *
   * @return `true` if associated context is computed, otherwise `false`
   */
  boolean isComputed();

  <R> R using(Function<T, R> mapper);

  /**
   * Create lazy evaluation context which uses provided source to compute value.
   * Not thread-safe.
   *
   * @param source evaluation source
   * @param <T> type of result value which will be computed
   * @return evaluation result
   */
  static <T> Lazy<T> eval(@Nonnull Supplier<T> source) {
    return Internals.evalUnsafe(source);
  }

  /**
   * Thread-safe version of the `eval` method.
   *
   * @param source evaluation source
   * @param <T> type of result value which will be computed
   * @return evaluation result
   */
  static <T> Lazy<T> evalSafe(@Nonnull Supplier<T> source) {
    return Internals.evalSafe(source);
  }
}
