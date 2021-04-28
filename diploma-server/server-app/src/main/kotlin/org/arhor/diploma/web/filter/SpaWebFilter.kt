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
            if (exchange.request.uri.path.let { !it.startsWith(SERVER_API_PREFIX) && it.matches(PATTERN) }) {
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

    companion object {
        private const val SERVER_API_PREFIX = "/api"
        private const val SERVER_ROOT_PATH = "/"
        private val PATTERN = "[^\\\\.]*".toRegex()
    }
}