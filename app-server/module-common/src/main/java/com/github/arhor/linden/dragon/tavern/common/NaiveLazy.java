package com.github.arhor.linden.dragon.tavern.common;

import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

final class NaiveLazy<T> extends AbstractLazy<T> {

    NaiveLazy(@Nonnull Supplier<T> source) {
        super(source);
    }

    @Override
    public T get() {
        return computed ? value : compute();
    }

    @Nonnull
    @Override
    public <R> NaiveLazy<R> map(@Nonnull final Function<T, R> f) {
        return new NaiveLazy<>(() -> f.apply(get()));
    }
}
