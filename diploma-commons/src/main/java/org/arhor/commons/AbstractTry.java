package org.arhor.commons;

public abstract class AbstractTry<T> implements Try<T> {

  private final T value;

  public AbstractTry(final T value) {
    this.value = value;
  }

  @Override
  public final T get() {
    return value;
  }
}
