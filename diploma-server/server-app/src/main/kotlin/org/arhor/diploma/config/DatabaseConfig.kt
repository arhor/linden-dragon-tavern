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

        val rawDbUri = System.getenv("DATABASE_URL")
        val jdbcDbUri = System.getenv("JDBC_DATABASE_URL")
        val dbUri = URI(rawDbUri)

        val (username, password) = dbUri.userInfo.split(":").toTypedArray()

        log.info { rawDbUri }
        log.info { jdbcDbUri }
        log.info { dbUri }
        log.info { jdbcDbUri.replace("jdbc", "r2dbc") }

        return ConnectionFactories.get(
//            "r2dbc:postgres://${username}:${password}@${dbUri.host}:${dbUri.port}/${dbUri.path}"
            jdbcDbUri.replace("jdbc", "r2dbc")
        )
    }
}
