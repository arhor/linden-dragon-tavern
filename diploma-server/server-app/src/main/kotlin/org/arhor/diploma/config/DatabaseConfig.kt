package org.arhor.diploma.config

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import mu.KotlinLogging
import org.arhor.diploma.util.SpringProfile
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.net.URI

val log = KotlinLogging.logger {}

@Configuration
@EnableR2dbcRepositories("org.arhor.diploma")
@EnableTransactionManagement
class DatabaseConfig {

    @Bean
    @Profile("!${SpringProfile.DEVELOPMENT}")
    fun connectionFactory(): ConnectionFactory {
        val uri = URI(System.getenv("DATABASE_URL"))

        val (username, password) = uri.userInfo.split(":").toTypedArray()

        return ConnectionFactories.get("r2dbcs:postgres://${username}:${password}@${uri.host}:${uri.port}/${uri.path}")
    }
}
