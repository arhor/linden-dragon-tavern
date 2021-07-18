package org.arhor.diploma.dnd.web.api

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.queryParamOrNull

private const val DEFAULT_PAGE = 0
private const val DEFAULT_SIZE = 200

private object Params {
    const val PAGE = "page"
    const val SIZE = "size"
}

internal fun ServerRequest.intQueryParam(name: String, default: Int, required: Boolean = false): Int {
    return queryParamOrNull(name)?.toInt()
        ?: if (required) throw IllegalArgumentException() else default
}

internal fun ServerRequest.getPage(required: Boolean = false): Int {
    return intQueryParam(Params.PAGE, DEFAULT_PAGE, required)
}

internal fun ServerRequest.getSize(required: Boolean = false): Int {
    return intQueryParam(Params.SIZE, DEFAULT_SIZE, required)
}
