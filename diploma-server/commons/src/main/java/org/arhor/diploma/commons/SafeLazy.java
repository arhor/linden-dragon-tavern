package org.arhor.diploma.commons;

import javax.annotation.Nonnull;
import java.util.function.Function;
import java.util.function.Supplier;

final class SafeLazy<T> extends AbstractLazy<T> {

    @Nonnull
    private final Object lock = new Object();

    SafeLazy(@Nonnull Supplier<T> source) {
        super(source);
    }

    @Override
    public final T get() {
        if (!computed) {
            synchronized (lock) {
                return compute();
            }
        }
        return value;
    }

    @Nonnull
    @Override
    public <R> Lazy<R> map(@Nonnull final Function<T, R> f) {
        return new SafeLazy<>(() -> f.apply(this.get()));
    }
}
