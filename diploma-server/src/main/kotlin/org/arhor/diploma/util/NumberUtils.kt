package org.arhor.diploma.util;

import java.util.function.ToIntFunction;

/**
 * Class provides various utility functions to work with numbers.
 *
 * @author Maksim_Buryshynets
 * @version 1
 */
object NumberUtils {

  /**
   * Method supposed to be used for creation number bounding function,
   * which guaranties that it will return passed value only in case that
   * it is higher than bound.
   *
   * @param bound min value to be returned by created function
   * @param <N> any number type
   * @return closure with min bound value
   */
  @JvmStatic
  fun <N : Number> minBound(bound: Int): ToIntFunction<N> {
    return ToIntFunction { arg ->
      if ((arg == null) || (arg.toLong() <= bound)) bound else arg.toInt()
    }
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
  @JvmStatic
  fun <N : Number> maxBound(bound: Int): ToIntFunction<N> {
    return ToIntFunction { arg ->
      if ((arg == null) || (arg.toLong() >= bound)) bound else arg.toInt()
    }
  }

}
