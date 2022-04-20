package com.github.arhor.linden.dragon.tavern.config

import com.github.arhor.linden.dragon.tavern.common.time.DEFAULT_ZONE_ID
import com.github.arhor.linden.dragon.tavern.common.time.TIME_MEASUREMENT_ACCURACY
import com.github.arhor.linden.dragon.tavern.util.SpringProfile
import mu.KotlinLogging
import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.net.URI
import java.time.Clock
import java.util.Optional


@Configuration(proxyBeanMethods = false)
@EnableJdbcAuditing(modifyOnCreate = false, dateTimeProviderRef = "instantDateTimeProviderUTC")
@EnableJdbcRepositories(basePackages = ["com.github.arhor.linden.dragon.tavern"])
@EnableTransactionManagement
class DatabaseConfig(private val env: Environment) {

    @Bean
    fun instantDateTimeProviderUTC() = DateTimeProvider {
        Optional.of(
            Clock.system(DEFAULT_ZONE_ID)
                .instant()
                .truncatedTo(TIME_MEASUREMENT_ACCURACY)
        )
    }

    @Bean(initMethod = "migrate")
    @ConditionalOnProperty(name = ["spring.flyway.enabled"], havingValue = "true")
    fun flyway(): Flyway {
        log.debug("Configuring flyway instance to apply migrations")

        val flywayConfig = Flyway.configure()
            .baselineOnMigrate(true)
            .dataSource(
                env.getRequiredProperty("spring.flyway.url"),
                env.getRequiredProperty("spring.flyway.user"),
                env.getRequiredProperty("spring.flyway.password")
            )

        env.getProperty("spring.flyway.locations")?.let {
            flywayConfig.locations(*it.split(",").toTypedArray())
        }

        return flywayConfig.load()
    }

    @Bean
    @Primary
    @Profile(SpringProfile.HEROKU)
    fun r2dbcProperties(): R2dbcProperties {
        log.debug("Using custom R2DBC config override")

        val uri = URI(
            checkNotNull(System.getenv(Companion.DATABASE_URL)) { "${Companion.DATABASE_URL} environment variable is missing" }
        )

        val (uriUsername, uriPassword) = uri.userInfo.split(":")

        return R2dbcProperties().apply {
            url = "r2dbc:postgres://${uri.host}:${uri.port}${uri.path}"
            username = uriUsername
            password = uriPassword
            properties["sslMode"] = "require"
            properties["ssl"] = "true"
        }
    }

    companion object {
        private const val DATABASE_URL = "DATABASE_URL"
        private val log = KotlinLogging.logger {}
    }
}
