package org.arhor.commons.pattern.observer;

public interface ObservableVal<T> extends Observable<T> {

  void set(T value);

  T get();

}
