package org.arhor.diploma.data.persistence.repository;

import org.arhor.diploma.TestConfig;
import org.arhor.diploma.testutils.IntegrationTest;
import org.arhor.diploma.testutils.RandomParameter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@IntegrationTest
@SpringBootTest(classes = {TestConfig.class})
@Testcontainers(disabledWithoutDocker = true)
@ExtendWith({RandomParameter.Resolver.class, SpringExtension.class})
public abstract class DatabaseIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:11.7")
                    .withDatabaseName("diploma_test_db")
                    .withUsername("postgres")
                    .withPassword("password");
}
