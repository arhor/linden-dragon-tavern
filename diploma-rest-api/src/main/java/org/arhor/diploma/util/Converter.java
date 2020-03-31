package org.arhor.diploma.util;

@FunctionalInterface
public interface Converter {

  /**
   * General method to convert object of one type to another.
   * Serves to avoid tight coupling with object mapping frameworks.
   *
   * @param item object to convert
   * @param targetClass destination type
   * @param <T> source type
   * @param <R> return type
   * @return converted object
   */
  <T, R> R convert(T item, Class<R> targetClass);
}
