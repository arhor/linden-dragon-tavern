package org.arhor.diploma.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement


@Configuration
@EnableJdbcRepositories("org.arhor.diploma")
@EnableTransactionManagement
class DatabaseConfig
