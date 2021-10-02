package org.arhor.diploma.commons;

import javax.annotation.Nonnull;
import java.util.function.Function;
import java.util.function.Supplier;

final class SafeLazy<T> extends AbstractLazy<T> {

    @Nonnull
    private final Object lock = new Object();

    private volatile boolean safeComputed;

    SafeLazy(@Nonnull Supplier<T> source) {
        super(source);
    }

    @Override
    public T get() {
        if (!safeComputed) {
            synchronized (lock) {
                if (!safeComputed) {
                    final T value = compute();
                    safeComputed = true;
                    return value;
                }
            }
        }
        return value;
    }

    @Override
    public boolean isComputed() {
        return safeComputed;
    }

    @Nonnull
    @Override
    public <R> Lazy<R> map(@Nonnull final Function<T, R> f) {
        return new SafeLazy<>(() -> f.apply(get()));
    }
}
