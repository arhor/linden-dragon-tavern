package org.arhor.diploma.web.filter

import mu.KLogging
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class SpaWebFilter : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return chain.filter(
            if (shouldBeForwardedToTheRootPath(exchange.request.uri.path)) {
                logger.debug { "Forwarding request from: '${exchange.request.uri.path}' to the '$SERVER_ROOT_PATH'" }
                exchange.mutate()
                    .request(
                        exchange.request.mutate()
                            .path(SERVER_ROOT_PATH)
                            .build()
                    ).build()
            } else {
                exchange
            }
        )
    }

    internal fun shouldBeForwardedToTheRootPath(path: String): Boolean {
        for (validServerApiPathPrefix in VALID_SERVER_API_PATHS) {
            if (path.startsWith(validServerApiPathPrefix)) {
                return false
            }
        }
        return when (path) {
            SERVER_ROOT_PATH -> false
            INDEX_PAGE_PATH -> true
            else -> path.matches(PATH_WITHOUT_NESTED_ELEMENTS)
        }
    }

    companion object : KLogging() {
        private const val INDEX_PAGE_PATH = "/index.html"
        private const val SERVER_ROOT_PATH = "/"
        private val VALID_SERVER_API_PATHS: Array<out String> = arrayOf("/api", "/actuator")
        private val PATH_WITHOUT_NESTED_ELEMENTS = Regex("[^\\\\.]*")
    }
}