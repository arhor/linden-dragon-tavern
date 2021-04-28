package org.arhor.diploma.data.persistence.repository;

import org.arhor.diploma.testutils.IntegrationTest
import org.arhor.diploma.testutils.RandomParameter
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.core.env.Environment
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@IntegrationTest
@DataR2dbcTest
@Testcontainers(disabledWithoutDocker = true)
@ExtendWith(RandomParameter.Resolver::class)
@Import(DatabaseIntegrationTest.TestDatabaseConfig::class)
internal abstract class DatabaseIntegrationTest {

    @TestConfiguration
    class TestDatabaseConfig(private val env: Environment) {

        @Bean(initMethod = "migrate")
        @ConditionalOnProperty(prefix = "flyway", name = ["enabled"], havingValue = "true")
        fun flyway() = Flyway(
            Flyway.configure()
                .baselineOnMigrate(true)
                .locations(
                    env.getRequiredProperty("flyway.locations")
                )
                .dataSource(
                    env.getRequiredProperty("flyway.url"),
                    env.getRequiredProperty("flyway.username"),
                    env.getRequiredProperty("flyway.password")
                )
        )
    }

    companion object {
        @JvmStatic
        @Container
        private val db = PostgreSQLContainer<PostgreSQLContainer<*>>("postgres:11.7")

        @JvmStatic
        @DynamicPropertySource
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {

            val databaseUrl = "postgresql://${db.host}:${db.firstMappedPort}/${db.databaseName}"
            val username = db.username
            val password = db.password

            registry.apply {
                add("spring.r2dbc.url") { "r2dbc:${databaseUrl}" }
                add("spring.r2dbc.username") { username }
                add("spring.r2dbc.password") { password }

                add("flyway.url") { "jdbc:${databaseUrl}" }
                add("flyway.username") { username }
                add("flyway.password") { password }
                add("flyway.locations") { "classpath:/db/migration" }
                add("flyway.enabled") { true }
            }
        }
    }
}
