package org.arhor.commons.pattern.observer;

public interface Observer<T> {

  void notice(T value);
}
