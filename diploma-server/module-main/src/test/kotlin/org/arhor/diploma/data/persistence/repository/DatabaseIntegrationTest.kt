package org.arhor.diploma.data.persistence.repository;

import org.arhor.diploma.config.DatabaseConfig
import org.arhor.diploma.testutils.RandomParameter
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataR2dbcTest
@Testcontainers(disabledWithoutDocker = true)
@ExtendWith(RandomParameter.Resolver::class)
@ContextConfiguration(classes = [DatabaseConfig::class])
abstract class DatabaseIntegrationTest {

    companion object {
        @JvmStatic
        @Container
        private val db = PostgreSQLContainer<Nothing>("postgres:11.7")

        @JvmStatic
        @DynamicPropertySource
        fun registerDynamicProps(registry: DynamicPropertyRegistry) {

            val databaseUrl = "postgresql://${db.host}:${db.firstMappedPort}/${db.databaseName}"
            val username = db.username
            val password = db.password

            registry.apply {
                add("spring.r2dbc.url") { "r2dbc:${databaseUrl}" }
                add("spring.r2dbc.username") { username }
                add("spring.r2dbc.password") { password }

                add("spring.flyway.url") { "jdbc:${databaseUrl}" }
                add("spring.flyway.user") { username }
                add("spring.flyway.password") { password }
                add("spring.flyway.locations") { "classpath:/db/migration" }
                add("spring.flyway.enabled") { true }
            }
        }
    }
}
