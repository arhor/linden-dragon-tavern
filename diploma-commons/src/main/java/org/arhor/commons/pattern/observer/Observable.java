package org.arhor.commons.pattern.observer;

import javax.annotation.Nonnull;

public interface Observable<T> {

  void subscribe(Observer<T> observer);

  void unsubscribe(Observer<T> observer);

  /* Basic factory methods */

  static <R> ObservableRef<R> ofRef(@Nonnull R value) {
    return Internals.observableRef(value);
  }

  static <V> ObservableVal<V> ofVal(@Nonnull V value) {
    return Internals.observableVal(value);
  }



  static <T extends Number & Comparable<T>> ObservableVal<T> of(@Nonnull T value) {
    return ofVal(value);
  }

  static ObservableVal<Boolean> of(@Nonnull Boolean value) {
    return ofVal(value);
  }

  static ObservableVal<Character> of(@Nonnull Character value) {
    return ofVal(value);
  }

  static ObservableVal<String> of(@Nonnull String value) {
    return ofVal(value);
  }

  static <T> ObservableRef<T> of(@Nonnull T value) {
    return ofRef(value);
  }
}
