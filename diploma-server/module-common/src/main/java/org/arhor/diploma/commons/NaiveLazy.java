package org.arhor.diploma.commons;

import javax.annotation.Nonnull;
import java.util.function.Function;
import java.util.function.Supplier;

final class NaiveLazy<T> extends AbstractLazy<T> {

    NaiveLazy(@Nonnull Supplier<T> source) {
        super(source);
    }

    @Override
    public final T get() {
        return computed ? value : compute();
    }

    @Nonnull
    @Override
    public <R> NaiveLazy<R> map(@Nonnull final Function<T, R> f) {
        return new NaiveLazy<>(() -> f.apply(this.get()));
    }
}
