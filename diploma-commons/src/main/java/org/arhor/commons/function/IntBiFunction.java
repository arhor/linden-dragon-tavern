package org.arhor.diploma.function;

@FunctionalInterface
public interface IntBiFunction<T> {

  T apply(int x, int y);

}
