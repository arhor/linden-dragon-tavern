package org.arhor.diploma.util

import java.util.function.BiFunction

const val DEFAULT_PAGE = 0
const val DEFAULT_SIZE = 200

/**
 * IntBiFunction MUST be returned as BiFunction to be able handle `null` arguments.
 *
 * @param request function which argument should be bounded
 * @param <N> Numeric type of arguments for resulting function
 * @param <T> return type of resulting function
 * @return bounded Function which consumes bounded arguments
 */
internal inline fun <N : Number, T> bound(crossinline request: (Int?, Int?) -> T): BiFunction<N, N, T> {
  return BiFunction { p, s ->
    request(boundPage(p), boundSize(s))
  }
}

private fun <N : Number> boundPage(page: N): Int {
  return minBound<N>(DEFAULT_PAGE).applyAsInt(page)
}

private fun <N : Number> boundSize(size: N): Int {
  return maxBound<N>(DEFAULT_SIZE).applyAsInt(size)
}
