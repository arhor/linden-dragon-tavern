package org.arhor.diploma.commons;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@FunctionalInterface
public interface RichSupplier<T> extends Supplier<T> {

    default <R> RichSupplier<R> map(Function<T, R> f) {
        return () -> f.apply(this.get());
    }

    default <R, S extends Supplier<? extends R>> RichSupplier<R> flatMap(Function<T, S> f) {
        return () -> f.apply(this.get()).get();
    }

    default <R, U> RichSupplier<U> merge(Supplier<R> that, BiFunction<T, R, U> f) {
        return () -> f.apply(this.get(), that.get());
    }

    default void forEach(Consumer<T> action) {
        action.accept(get());
    }

    default Stream<T> stream() {
        return Stream.generate(this);
    }

}
