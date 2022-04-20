package com.github.arhor.linden.dragon.tavern.common;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
@FunctionalInterface
public interface RichSupplier<T> extends Supplier<T> {

    @Nonnull
    default <R> RichSupplier<R> map(@Nonnull Function<T, R> f) {
        return () -> f.apply(this.get());
    }

    @Nonnull
    default <R, S extends Supplier<? extends R>> RichSupplier<R> flatMap(@Nonnull Function<T, S> f) {
        return () -> f.apply(this.get()).get();
    }

    @Nonnull
    default <R, U> RichSupplier<U> merge(@Nonnull Supplier<R> that, @Nonnull BiFunction<T, R, U> f) {
        return () -> f.apply(this.get(), that.get());
    }

    default void forEach(@Nonnull Consumer<T> action) {
        action.accept(get());
    }

    @Nonnull
    default Stream<T> stream() {
        return Stream.generate(this);
    }

    default Optional<T> optional() {
        return  Optional.ofNullable(get());
    }
}
