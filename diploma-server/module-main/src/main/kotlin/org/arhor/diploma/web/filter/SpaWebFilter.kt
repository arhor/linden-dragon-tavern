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
            if (exchange.shouldBeForwardedToTheRootPath()) {
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

    private fun ServerWebExchange.shouldBeForwardedToTheRootPath(): Boolean {
        val path = request.uri.path
        for (validServerApiPathPrefix in VALID_SERVER_API_PATHS) {
            if (path.startsWith(validServerApiPathPrefix)) {
                return false
            }
        }
        return path.matches(PATTERN)
    }

    companion object {
        private const val SERVER_ROOT_PATH = "/"
        private val VALID_SERVER_API_PATHS: Array<out String> = arrayOf("/api", "/actuator")
        private val PATTERN = "[^\\\\.]*".toRegex()
    }
}