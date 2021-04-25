package org.arhor.diploma.config

import org.arhor.diploma.util.SpringProfile
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.net.URI

@Configuration
@EnableR2dbcRepositories("org.arhor.diploma")
@EnableTransactionManagement
class DatabaseConfig {

    @Bean
    @Primary
    @Profile(SpringProfile.PRODUCTION)
    fun r2dbcProperties(): R2dbcProperties {
        val uri = URI(System.getenv("DATABASE_URL"))

        val (username, password) = uri.userInfo.split(":")

        return R2dbcProperties().apply {
            this.url = "r2dbc:postgres://${uri.host}:${uri.port}/${uri.path}"
            this.username = username
            this.password = password
            this.properties["sslMode"] = "require"
            this.properties["ssl"] = "true"
        }
    }
}
