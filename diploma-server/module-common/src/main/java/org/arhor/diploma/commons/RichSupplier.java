package org.arhor.diploma.commons;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
}
