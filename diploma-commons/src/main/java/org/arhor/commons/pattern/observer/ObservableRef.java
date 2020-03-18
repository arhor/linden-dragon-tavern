package org.arhor.commons.pattern.observer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ObservableRef<T> extends Observable<T> {

  <V> void mutate(BiConsumer<T, V> setter, V value);

  <V> V access(Function<T, V> getter);

  <V> Consumer<V> buildSetter(BiConsumer<T, V> setter);

  <V> Supplier<V> buildGetter(Function<T, V> getter);
}
