package org.arhor.diploma.web.util;


import org.arhor.commons.function.IntBiFunction;
import org.arhor.commons.util.NumberUtils;

import java.util.function.BiFunction;

@SuppressWarnings("unused")
public final class PageUtils {

  public static final int DEFAULT_PAGE = 0;
  public static final int DEFAULT_SIZE = 50;

  private PageUtils() {}

  /**
   * IntBiFunction MUST be returned as BiFunction to be able handle `null` arguments.
   *
   * @param request function which argument should be bounded
   * @param <N> Numeric type of arguments for resulting function
   * @param <T> return type of resulting function
   * @return bounded Function which consumes bounded arguments
   */
  public static <N extends Number, T> BiFunction<N, N, T> bound(IntBiFunction<T> request) {
    return (p, s) -> request.apply(boundPage(p), boundSize(s));
  }

  private static <N extends Number> int boundPage(N page) {
    return NumberUtils.minBound(DEFAULT_PAGE).applyAsInt(page);
  }

  private static <N extends Number> int boundSize(N size) {
    return NumberUtils.maxBound(DEFAULT_SIZE).applyAsInt(size);
  }
}
