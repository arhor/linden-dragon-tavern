package org.arhor.diploma.web

import org.arhor.diploma.web.api.v1.AccountHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MainRouterConfig {

    @Bean
    fun mainRouter(accountHandler: AccountHandler) = coRouter {

        "/api/v1".nest {

            "/accounts".nest {
                GET("", accountHandler::getAccounts)
            }
        }
    }
}