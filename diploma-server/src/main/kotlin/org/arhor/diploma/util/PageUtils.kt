package org.arhor.diploma.util


import java.util.function.BiFunction

object PageUtils {

  private const val DEFAULT_PAGE = 0
  private const val DEFAULT_SIZE = 200

  /**
   * IntBiFunction MUST be returned as BiFunction to be able handle `null` arguments.
   *
   * @param request function which argument should be bounded
   * @param <N> Numeric type of arguments for resulting function
   * @param <T> return type of resulting function
   * @return bounded Function which consumes bounded arguments
   */
  @JvmStatic
  fun <N : Number, T> bound(request: (Int, Int) -> T): BiFunction<N, N, T> {
    return BiFunction { p, s ->
      request(boundPage(p), boundSize(s))
    }
  }

  @JvmStatic
  private fun <N : Number> boundPage(page: N): Int {
    return NumberUtils.minBound<N>(DEFAULT_PAGE).applyAsInt(page)
  }

  @JvmStatic
  private fun <N : Number> boundSize(size: N): Int {
    return NumberUtils.maxBound<N>(DEFAULT_SIZE).applyAsInt(size)
  }
}
