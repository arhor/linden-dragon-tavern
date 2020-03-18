package org.arhor.commons.function;

@FunctionalInterface
public interface IntBiFunction<T> {

  T apply(int x, int y);

}
