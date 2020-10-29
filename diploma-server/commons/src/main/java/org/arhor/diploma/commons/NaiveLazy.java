package org.arhor.diploma.commons;

import java.util.function.Function;
import java.util.function.Supplier;

final class NaiveLazy<T> extends AbstractLazy<T> {

    NaiveLazy(Supplier<T> source) {
        super(source);
    }

    @Override
    public final T get() {
        return computed ? value : compute();
    }

    @Override
    public <R> NaiveLazy<R> map(final Function<T, R> f) {
        return new NaiveLazy<>(() -> f.apply(this.get()));
    }
}
