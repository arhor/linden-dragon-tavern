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
        val dbUri = URI(System.getenv("DATABASE_URL"))

        val (username, password) = dbUri.userInfo.split(":").toTypedArray()

        log.info { "r2dbc:postgresql://${dbUri.host}:${dbUri.port}${dbUri.path}?user=${username}&password=${password}" }

        return ConnectionFactories.get(
            "r2dbc:postgresql://${dbUri.host}:${dbUri.port}${dbUri.path}?user=${username}&password=${password}"
        )
    }
}
