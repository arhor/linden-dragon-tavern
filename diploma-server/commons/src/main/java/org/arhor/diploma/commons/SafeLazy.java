package org.arhor.diploma.commons;

import java.util.function.Function;
import java.util.function.Supplier;

final class SafeLazy<T> extends AbstractLazy<T> {

    private final Object lock = new Object();

    SafeLazy(Supplier<T> source) {
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

    @Override
    public <R> Lazy<R> map(final Function<T, R> f) {
        return new SafeLazy<>(() -> f.apply(this.get()));
    }
}
