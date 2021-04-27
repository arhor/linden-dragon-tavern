package org.arhor.diploma.web

import org.arhor.diploma.web.api.v1.AccountHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration(proxyBeanMethods = false)
class MainRouterConfig {

    @Bean
    fun mainRouter(accountHandler: AccountHandler) = coRouter {
        // @formatter:off
        "/api/v1".nest {

            "/accounts".nest {
                GET(""     , accountHandler::getAccounts)
                GET("/{id}", accountHandler::getAccount)
            }
        }
        // @formatter:on
    }
}