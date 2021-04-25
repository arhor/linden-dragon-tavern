package org.arhor.diploma.web.filter

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class SpaWebFilter : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return chain.filter(
            if (isNotValidApiPath(exchange.request.uri.path)) {
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

    private fun isNotValidApiPath(path: String): Boolean {
        return !path.startsWith(SERVER_API_PREFIX) && path.matches(PATTERN)
    }

    companion object {
        private const val SERVER_API_PREFIX = "/api"
        private const val SERVER_ROOT_PATH = "/"
        private val PATTERN = "[^\\\\.]*".toRegex()
    }
}