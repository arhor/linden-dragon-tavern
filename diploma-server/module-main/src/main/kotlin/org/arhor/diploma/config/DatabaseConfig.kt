package org.arhor.diploma.config

import mu.KotlinLogging
import org.arhor.diploma.data.persistence.domain.core.AuditableDomainObject
import org.arhor.diploma.util.SpringProfile
import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.mapping.event.AfterConvertCallback
import org.springframework.data.r2dbc.mapping.event.AfterSaveCallback
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import reactor.core.publisher.Mono
import java.net.URI

private val log = KotlinLogging.logger {}

private const val DATABASE_URL = "DATABASE_URL"

@Configuration(proxyBeanMethods = false)
@EnableR2dbcAuditing(modifyOnCreate = false)
@EnableR2dbcRepositories("org.arhor.diploma")
@EnableTransactionManagement
class DatabaseConfig(private val env: Environment) {

    @Bean(initMethod = "migrate")
    @ConditionalOnProperty(prefix = "spring.flyway", name = ["enabled"], havingValue = "true")
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

        env.getRequiredProperty("")

        val uri = URI(
            checkNotNull(System.getenv(DATABASE_URL)) { "$DATABASE_URL environment variable is missing" }
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

    @Bean
    fun beforeConvertCallback(): BeforeConvertCallback<AuditableDomainObject<*>> {
        return BeforeConvertCallback { entity, _ ->
            log.debug { "before convert: $entity" }
            Mono.just(entity)
        }
    }

    @Bean
    fun afterConvertCallback(): AfterConvertCallback<AuditableDomainObject<*>> {
        return AfterConvertCallback { entity, _ ->
            log.debug { "after convert: $entity" }
            Mono.just(entity)
        }
    }

    @Bean
    fun beforeSaveCallback(): BeforeSaveCallback<AuditableDomainObject<*>> {
        return BeforeSaveCallback { entity, _, _ ->
            log.debug { "before save: $entity" }
            Mono.just(entity)
        }
    }

    @Bean
    fun afterSaveCallback(): AfterSaveCallback<AuditableDomainObject<*>> {
        return AfterSaveCallback { entity, _, _ ->
            log.debug { "after save: $entity" }
            Mono.just(entity)
        }
    }
}
