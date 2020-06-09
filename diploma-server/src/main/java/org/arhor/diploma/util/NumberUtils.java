package org.arhor.diploma.util;

import java.util.function.ToIntFunction;

/**
 * Class provides various utility functions to work with numbers.
 *
 * @author Maksim_Buryshynets
 * @version 1
 */
@SuppressWarnings("unused")
public final class NumberUtils {

  private NumberUtils() {
    throw new UnsupportedOperationException("Must not be instantiated");
  }

  /**
   * Method supposed to be used for creation number bounding function,
   * which guaranties that it will return passed value only in case that
   * it is higher than bound.
   *
   * @param bound min value to be returned by created function
   * @param <N> any number type
   * @return closure with min bound value
   */
  public static
  <N extends Number> ToIntFunction<N> minBound(final int bound) {
    return arg ->
        ((arg == null) || (arg.longValue() <= bound))
            ? bound
            : arg.intValue();
  }

  /**
   * Method supposed to be used for creation number bounding function,
   * which guaranties that it will return passed value only in case that
   * it is lower than bound.
   *
   * @param bound max value to be returned by created function
   * @param <N> any number type
   * @return closure with max bound value
   */
  public static
  <N extends Number> ToIntFunction<N> maxBound(final int bound) {
    return arg ->
        ((arg == null) || (arg.longValue() >= bound))
            ? bound
            : arg.intValue();
  }

}
