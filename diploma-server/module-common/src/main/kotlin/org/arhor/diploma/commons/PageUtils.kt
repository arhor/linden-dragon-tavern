package org.arhor.diploma.commons

const val DEFAULT_PAGE = 0
const val DEFAULT_SIZE = 200

internal inline fun <N : Number, T> bound(
    crossinline request: (Int, Int) -> T
): (N?, N?) -> T {
    return { page, size ->
        val safePage = page.minBound(DEFAULT_PAGE)
        val safeSize = size.maxBound(DEFAULT_SIZE)
        request(safePage, safeSize)
    }
}
