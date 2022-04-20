package com.github.arhor.linden.dragon.tavern.data.persistence.repository

import com.github.arhor.linden.dragon.tavern.config.DatabaseConfig
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers(disabledWithoutDocker = true)
@ExtendWith(com.github.arhor.linden.dragon.tavern.testutils.RandomParameter.Resolver::class)
@ContextConfiguration(classes = [DatabaseConfig::class])
abstract class DatabaseIntegrationTest {

    companion object {
        @JvmStatic
        @Container
        private val db = PostgreSQLContainer<Nothing>("postgres:11.7")

        @JvmStatic
        @DynamicPropertySource
        fun registerDynamicProps(registry: DynamicPropertyRegistry) {
            val databaseJdbcUrl = db.jdbcUrl
            val databaseR2dbcUrl = "r2dbc:postgresql://${db.host}:${db.firstMappedPort}/${db.databaseName}"
            val username = db.username
            val password = db.password

            registry.apply {
                add("spring.datasource.url") { databaseJdbcUrl }
                add("spring.datasource.username") { username }
                add("spring.datasource.password") { password }

                add("spring.r2dbc.url") { databaseR2dbcUrl }
                add("spring.r2dbc.username") { username }
                add("spring.r2dbc.password") { password }

                add("spring.flyway.url") { databaseJdbcUrl }
                add("spring.flyway.user") { username }
                add("spring.flyway.password") { password }
                add("spring.flyway.locations") { "classpath:/db/migration" }
                add("spring.flyway.enabled") { true }
            }
        }
    }
}
