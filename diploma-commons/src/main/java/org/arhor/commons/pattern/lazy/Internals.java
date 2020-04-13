package org.arhor.commons.pattern.lazy;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

final class Internals {

  private Internals() {}

  static <T> NaiveLazy<T> evalUnsafe(Supplier<T> source) {
    return new NaiveLazy<>(source);
  }

  static <T> SafeLazy<T> evalSafe(Supplier<T> source) {
    return new SafeLazy<>(source);
  }

  private abstract static class AbstractLazy<T> implements Lazy<T> {

    private Supplier<T> source;

    protected boolean computed;
    protected T value;

    AbstractLazy(final Supplier<T> source) {
      Objects.requireNonNull(source, "Lazy evaluation source must not be null");
      this.source = source;
    }

    @Override
    public final boolean isComputed() {
      return computed;
    }

    protected final T compute() {
      if (!computed) {
        value = source.get();
        // Nullifying source reference allows to release
        // associated scope and prevent memory leaks
        source = null;
        computed = true;
      }
      return value;
    }
  }

  private static final class NaiveLazy<T> extends AbstractLazy<T> {

    NaiveLazy(Supplier<T> source) { super(source); }

    @Override
    public final T get() {
      return computed ? value : compute();
    }

    @Override
    public <R> NaiveLazy<R> map(final Function<T, R> f) {
      return Internals.evalUnsafe(() -> f.apply(this.get()));
    }
  }

  private static final class SafeLazy<T> extends AbstractLazy<T> {

    SafeLazy(Supplier<T> source) { super(source); }

    @Override
    public final T get() {
      if (!computed) {
        synchronized (this) {
          return compute();
        }
      }
      return value;
    }

    @Override
    public <R> SafeLazy<R> map(final Function<T, R> f) {
      return Internals.evalSafe(() -> f.apply(this.get()));
    }
  }
}
