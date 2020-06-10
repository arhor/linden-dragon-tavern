package org.arhor.diploma.util

interface Converter {

  /**
   * General method to convert object of one type to another.
   * Serves to avoid tight coupling with object mapping frameworks.
   *
   * @param source object to convert
   * @param target destination type
   * @param <T> source type
   * @param <R> return type
   * @return converted object
   */
  fun <T, R> convert(source: T, target: Class<R>): R
}
