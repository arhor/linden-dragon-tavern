package org.arhor.diploma;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
public abstract class DatabaseIntegrationTest {

  @Container
  public static final PostgreSQLContainer<?> POSTGRES =
      new PostgreSQLContainer<>("postgres:11.7")
          .withDatabaseName("diploma_test_db")
          .withUsername("postgres")
          .withPassword("password");
}
