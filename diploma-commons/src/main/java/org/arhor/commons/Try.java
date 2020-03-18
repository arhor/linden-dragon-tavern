package org.arhor.commons;

public interface Try<T> {

  T get();

  boolean isFailure();

}
